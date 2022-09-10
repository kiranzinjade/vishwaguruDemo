package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.VoucherDetails;
import com.techvg.vks.accounts.mapper.VoucherDetailsMapper;
import com.techvg.vks.accounts.model.VoucherDetailsDto;
import com.techvg.vks.accounts.repository.VoucherDetailsRepository;
import com.techvg.vks.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherDetailsServiceImpl implements VoucherDetailsService {
    private  final VoucherDetailsMapper transDetailsMapper;
    private final VoucherDetailsRepository transDetailsRepository;

    @Override
    public VoucherDetailsDto addVoucherDetails(VoucherDetailsDto transDetailsDto) {

            return transDetailsMapper.toDto(transDetailsRepository.save(
                    transDetailsMapper.toDomain(transDetailsDto)));
    }

    @Override
    public VoucherDetailsDto updateVoucherDetails(Long transId, VoucherDetailsDto transDetailsDto, Authentication authentication) {
        transDetailsRepository.findById(transId).orElseThrow(
                () -> new NotFoundException("No Voucher Details found for Id : " +transId));
        VoucherDetails transDetail = transDetailsMapper.toDomain(transDetailsDto);
        transDetail.setId(transId);
        return transDetailsMapper.toDto(transDetailsRepository.save(transDetail));
    }

    @Override
    public VoucherDetailsDto getVoucherDetailsById(Long transId) {

        VoucherDetails transDetail = transDetailsRepository.findById(transId).orElseThrow(
                () -> new NotFoundException("No Voucher Details found for Id : " +transId));
        return transDetailsMapper.toDto(transDetail);
    }

    @Override
    public VoucherDetailsDto deleteVoucherDetailsById(Long transId) {

        VoucherDetails transDetail = transDetailsRepository.findById(transId).orElseThrow(
                () -> new NotFoundException("No Voucher Details found for Id : " +transId));
        if (transDetail != null) {
            transDetail.setIsDeleted(true);
            transDetailsRepository.save(transDetail);
        }
        return transDetailsMapper.toDto(transDetail);
    }
}
