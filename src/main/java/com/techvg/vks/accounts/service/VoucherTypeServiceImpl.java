package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.VoucherType;
import com.techvg.vks.accounts.mapper.VoucherTypeMapper;
import com.techvg.vks.accounts.model.VoucherTypeDto;
import com.techvg.vks.accounts.model.VoucherTypePageList;
import com.techvg.vks.accounts.repository.VoucherTypeRepository;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherTypeServiceImpl implements VoucherTypeService {
    private final VoucherTypeRepository voucherTypeRepository;
    private final VoucherTypeMapper voucherTypeMapper;

    @Override
    public VoucherTypeDto addVoucherType(VoucherTypeDto voucherTypeDto) {
        Optional<VoucherType> voucherTypeObjOptional = voucherTypeRepository.findByNameAndIsDeleted(voucherTypeDto.getName(),false);
        if (voucherTypeObjOptional.isPresent()){
            throw new AlreadyExitsException("Voucher Type already exists : " + voucherTypeDto.getName());
        }
        else {
            return voucherTypeMapper.toDto(voucherTypeRepository.save(voucherTypeMapper.toDomain(voucherTypeDto)));
        }
    }
    
    @Override
	public List<VoucherTypeDto> listVouchers() {
		return voucherTypeMapper.domainToDtoList(voucherTypeRepository.findByIsDeleted(false));
	}

    @Override
    public List<VoucherTypeDto> listGeneralVouchers() {
        return voucherTypeMapper.domainToDtoList(voucherTypeRepository.findByIsGeneralAndIsDeleted(true,false));
    }

    @Override
    public VoucherTypeDto updateVoucherType(Long voucherTypeId, VoucherTypeDto voucherTypeDto, Authentication authentication) {
        voucherTypeRepository.findById(voucherTypeId).orElseThrow(
                () -> new NotFoundException("No Voucher Type found for Id : " +voucherTypeId));
        VoucherType voucherType = voucherTypeMapper.toDomain(voucherTypeDto);
        voucherType.setId(voucherTypeId);
        return voucherTypeMapper.toDto(voucherTypeRepository.save(voucherType));
    }

    @Override
    public VoucherTypeDto getVoucherTypeById(Long voucherTypeId) {
        VoucherType voucherType = voucherTypeRepository.findById(voucherTypeId).orElseThrow(
                () -> new NotFoundException("No Voucher Type found for Id : " +voucherTypeId));
        return voucherTypeMapper.toDto(voucherType);
    }

    @Override
    public VoucherTypeDto deleteVoucherTypeById(Long voucherTypeId) {

        VoucherType voucherType = voucherTypeRepository.findById(voucherTypeId).orElseThrow(
                () -> new NotFoundException("No Voucher Type found for Id : " +voucherTypeId));
        if (voucherType != null) {
            voucherType.setIsDeleted(true);
            voucherTypeRepository.save(voucherType);
        }
        return voucherTypeMapper.toDto(voucherType);
    }

    @Override
    public VoucherTypePageList listVoucherType(Pageable pageable) {

        Page<VoucherType> voucherTypePage;
        voucherTypePage = voucherTypeRepository.findByIsDeleted(false,pageable);

        return new VoucherTypePageList(voucherTypePage
                .getContent()
                .stream()
                .map(voucherTypeMapper::toDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(voucherTypePage.getPageable().getPageNumber(),
                                voucherTypePage.getPageable().getPageSize()),
                voucherTypePage.getTotalElements());
    }

    @Override
    public VoucherType getVoucherTypeByName(String name) {
        Optional<VoucherType> voucherTypeObjOptional = voucherTypeRepository.findByNameAndIsDeleted(name,false);
        return voucherTypeObjOptional.orElse(null);
    }
}
