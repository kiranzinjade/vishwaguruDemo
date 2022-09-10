package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.domain.VoucherDetails;
import com.techvg.vks.accounts.domain.VoucherTrans;
import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.accounts.mapper.VoucherTransMapper;
import com.techvg.vks.accounts.model.VoucherTransDto;
import com.techvg.vks.accounts.model.VoucherTransPageList;
import com.techvg.vks.accounts.repository.LedgerAccountsRepository;
import com.techvg.vks.accounts.repository.VoucherTransRepository;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.service.LoanDetailsService;
import com.techvg.vks.share.domain.MemberShareAcc;
import com.techvg.vks.share.service.SharesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherTransServiceImpl implements VoucherTransService {
    private final VoucherTransRepository voucherTransRepository;
    private final VoucherTransMapper voucherTransMapper;
    private final LedgerAccountsRepository ledgerAccountsRepository;
    private final LoanDetailsService loanDetailsService;
    private final SharesService sharesService;

    @Override
    @Transactional
    public List<VoucherTrans> addVoucherTranEntry(Vouchers vouchers) {
        List<VoucherTrans> voucherTransList = new ArrayList<>();
        VoucherTrans voucherTrans;
        Set<VoucherDetails> voucherDetails = vouchers.getVoucherDetails();

        for (VoucherDetails objVoucherDetails:voucherDetails) {
            voucherTrans = new VoucherTrans();
            voucherTrans.setVoucherNo(vouchers.getVoucherNo());
            voucherTrans.setRemark(objVoucherDetails.getAccountHead());
            voucherTrans.setTransDate(vouchers.getVoucherDate());
            voucherTrans.setMode(vouchers.getMode());
            voucherTrans.setDayBookCreated(false);
            if(objVoucherDetails.getCreditAcc() !=null) {

                LedgerAccounts ledgerAccount = ledgerAccountsRepository.findByAccountNo(objVoucherDetails.getCreditAcc());
                if(ledgerAccount == null) {
                    if(vouchers.getAppCode().equalsIgnoreCase(AccountConstants.LOAN)){
                       LoanDetails loanDetails= loanDetailsService.getLoanAccNo(objVoucherDetails.getCreditAcc());
                        ledgerAccount = ledgerAccountsRepository.findByAccHeadCode(loanDetails.getParentAccHeadCode());
                    }else if(vouchers.getAppCode().equalsIgnoreCase(AccountConstants.SHARE)){
                        MemberShareAcc shareAcc= sharesService.getMemberShareAccByAccNo(objVoucherDetails.getCreditAcc());
                        ledgerAccount = ledgerAccountsRepository.findByAccHeadCode(shareAcc.getParentAccHeadCode());
                    }else{
                        throw new NotFoundException("No Ledger Account found for account : " +objVoucherDetails.getCreditAcc());
                    }
                }
                        //.orElseThrow(
                        //() -> new NotFoundException("No Ledger Account found for account : " +objVoucherDetails.getCreditAcc()));
                voucherTrans.setLedgerAccounts(ledgerAccount);
                voucherTrans.setCreditAmt(objVoucherDetails.getTransAmount());
                voucherTrans.setTransType(AccountConstants.CREDIT);
                voucherTrans.setBalance(objVoucherDetails.getTransAmount());

            }
            else if (objVoucherDetails.getDebitAcc() !=null) {
                LedgerAccounts ledgerAccount = ledgerAccountsRepository.findByAccountNo(objVoucherDetails.getDebitAcc());
                        //.orElseThrow(
                        //() -> new NotFoundException("No Ledger Account found for account : " +objVoucherDetails.getDebitAcc()));
                if(ledgerAccount == null) {
                    if(vouchers.getAppCode().equalsIgnoreCase(AccountConstants.LOAN)){
                        LoanDetails loanDetails= loanDetailsService.getLoanAccNo(objVoucherDetails.getDebitAcc());
                        ledgerAccount = ledgerAccountsRepository.findByAccHeadCode(loanDetails.getParentAccHeadCode());
                    }else if(vouchers.getAppCode().equalsIgnoreCase(AccountConstants.SHARE)){
                        MemberShareAcc shareAcc= sharesService.getMemberShareAccByAccNo(objVoucherDetails.getDebitAcc());
                        ledgerAccount = ledgerAccountsRepository.findByAccHeadCode(shareAcc.getParentAccHeadCode());
                    }else{
                        throw new NotFoundException("No Ledger Account found for account : " +objVoucherDetails.getCreditAcc());
                    }
                }
                voucherTrans.setLedgerAccounts(ledgerAccount);
                voucherTrans.setBalance(ledgerAccount.getAccBalance() - objVoucherDetails.getTransAmount());
                voucherTrans.setDebitAmt(objVoucherDetails.getTransAmount());
                voucherTrans.setTransType(AccountConstants.DEBIT);
                voucherTrans.setBalance(objVoucherDetails.getTransAmount());

            }
            voucherTransRepository.saveAndFlush(voucherTrans);
            voucherTransList.add(voucherTrans);
        }
        return voucherTransList;
    }

    @Override
    public VoucherTransDto addVoucherTrans(VoucherTransDto voucherTransDto) {
        return voucherTransMapper.toDto(voucherTransRepository
              .save(voucherTransMapper.toDomain(voucherTransDto)));
    }

    @Override
    public VoucherTransDto updateVoucherTrans(Long voucherTransId, VoucherTransDto voucherTransDto, Authentication authentication) {
        voucherTransRepository.findById(voucherTransId).orElseThrow(
                () -> new NotFoundException("No Voucher Transaction found for Id : " +voucherTransId));
        VoucherTrans voucherTrans = voucherTransMapper.toDomain(voucherTransDto);
        voucherTrans.setId(voucherTransId);
        return voucherTransMapper.toDto(voucherTransRepository.save(voucherTrans));
    }

    @Override
    public VoucherTransDto getVoucherTransById(Long voucherTransId) {
        VoucherTrans voucherTrans = voucherTransRepository.findById(voucherTransId).orElseThrow(
                () -> new NotFoundException("No Voucher Transaction found for Id : " +voucherTransId));
        return voucherTransMapper.toDto(voucherTrans);
    }

    @Override
    public VoucherTransDto deleteVoucherTransById(Long voucherTransId) {
        VoucherTrans voucherTrans = voucherTransRepository.findById(voucherTransId).orElseThrow(
                () -> new NotFoundException("No Voucher Transaction found for Id : " +voucherTransId));
        if (voucherTrans != null) {
            voucherTrans.setIsDeleted(true);
            voucherTransRepository.save(voucherTrans);
        }
        return voucherTransMapper.toDto(voucherTrans);
    }

    @Override
    public VoucherTransPageList listVoucherTrans(Pageable pageable) {
        Page<VoucherTrans> voucherTransPage;
        voucherTransPage = voucherTransRepository.findAll(pageable);

        return new VoucherTransPageList(voucherTransPage
                .getContent()
                .stream()
                .map(voucherTransMapper::toDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(voucherTransPage.getPageable().getPageNumber(),
                                voucherTransPage.getPageable().getPageSize()),
                voucherTransPage.getTotalElements());
    }
}
