package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.VoucherTrans;
import com.techvg.vks.accounts.domain.VoucherType;
import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.accounts.mapper.VouchersMapper;
import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.accounts.model.VouchersPageList;
import com.techvg.vks.accounts.repository.VoucherTypeRepository;
import com.techvg.vks.accounts.repository.VouchersRepository;
import com.techvg.vks.common.LoginUser;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.idgenerator.VoucherSeq;
import com.techvg.vks.idgenerator.repository.VoucherSeqRepository;
import com.techvg.vks.society.service.SocietyBankTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VouchersServiceImpl implements VouchersService {

    private final VouchersRepository vouchersRepository;
    private final VouchersMapper vouchersMapper;
    private final VoucherTypeRepository voucherTypeRepository;
    private final VoucherSeqRepository voucherSeqRepository;

    private final VoucherTransService voucherTransService;
    private final CashBookService cashBookService;
    private final SocietyBankTransactionService societyBankTransactionService;

    @Override
    public VouchersDto addVouchers(VouchersDto vouchersDto, Authentication authentication) {
        Optional<Vouchers> vouchersObjOptional = vouchersRepository.findByVoucherNo(vouchersDto.getVoucherNo());
        if (vouchersObjOptional.isPresent()){
            throw new AlreadyExitsException("Voucher already exists : " + vouchersDto.getVoucherNo());
        }
        else {
            VoucherType voucherType = voucherTypeRepository.getOne(vouchersDto.getVoucherTypeId());
            Vouchers vouchers = vouchersMapper.toDomain(vouchersDto);
            vouchers.setVoucherType(voucherType);
            vouchers.setVoucherDate(new Date());
            vouchers.setPreparedBy(authentication.getName());
            //Generate Voucher No
            VoucherSeq no = new VoucherSeq();
            voucherSeqRepository.save(no);
            vouchers.setVoucherNo(no.getVoucherNo());
            return vouchersMapper.toDto(vouchersRepository.save(vouchers));
        }
    }

    @Override
    public boolean authoriseVoucher(List<Long> vouchersIdList) {
        if(vouchersIdList!=null){
            for (Long voucherId:vouchersIdList) {
                authoriseVoucher(voucherId);
            }
        }
        return true;
    }

    @Override
    public VouchersDto authoriseVoucher(Long vouchersId) {
        Vouchers vouchers = vouchersRepository.findById(vouchersId).orElseThrow(
                () -> new NotFoundException("No Voucher  found for Id for Authorisation: " +vouchersId));
        if (vouchers != null) {
            vouchers.setAuthorisedBy(LoginUser.getUser());
            vouchersRepository.save(vouchers);
            List<VoucherTrans> voucherTransList = voucherTransService.addVoucherTranEntry(vouchers);
            if(vouchers.getMode().equalsIgnoreCase(AccountConstants.CASH))
                 cashBookService.addCashbookEntry(voucherTransList);
            if((vouchers.getMode().equalsIgnoreCase(AccountConstants.CHEQUE)) ||
               (vouchers.getMode().equalsIgnoreCase(AccountConstants.TRANSFER)))
                societyBankTransactionService.addBankBookEntry(voucherTransList);
        }
        return vouchersMapper.toDto(vouchers);
    }

    @Override
    public VouchersDto updateVouchers(Long vouchersId, VouchersDto vouchersDto, Authentication authentication) {
        vouchersRepository.findById(vouchersId).orElseThrow(
                () -> new NotFoundException("No Voucher  found for Id for update: " +vouchersId));
        Vouchers vouchers = vouchersMapper.toDomain(vouchersDto);
        VoucherType voucherType = voucherTypeRepository.getOne(vouchersDto.getVoucherTypeId());
        vouchers.setVoucherType(voucherType);
        vouchers.setId(vouchersId);
        return vouchersMapper.toDto(vouchersRepository.save(vouchers));
    }

    @Override
    public VouchersDto getVouchersById(Long vouchersId) {
        Vouchers vouchers = vouchersRepository.findById(vouchersId).orElseThrow(
                () -> new NotFoundException("No Voucher  found for Id : " +vouchersId));
        return vouchersMapper.toDto(vouchers);
    }

    @Override
    public VouchersDto deleteVouchersById(Long vouchersId) {
        Vouchers vouchers = vouchersRepository.findById(vouchersId).orElseThrow(
                () -> new NotFoundException("No Voucher  found for Id : " +vouchersId));
        if (vouchers != null) {
            vouchers.setIsDeleted(true);
            vouchersRepository.save(vouchers);
        }
        return vouchersMapper.toDto(vouchers);
    }

    @Override
    public VouchersPageList listVouchers(Pageable pageable) {
        Page<Vouchers> vouchersPage;
        vouchersPage = vouchersRepository.findAll(pageable);

        return new VouchersPageList(vouchersPage
                .getContent()
                .stream()
                .map(vouchersMapper::toDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(vouchersPage.getPageable().getPageNumber(),
                                vouchersPage.getPageable().getPageSize()),
                vouchersPage.getTotalElements());
    }
}
