package com.techvg.vks.trading.service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.trading.domain.VendorRegister;
import com.techvg.vks.trading.mapper.VendorRegisterMapper;
import com.techvg.vks.trading.model.VendorRegisterDto;
import com.techvg.vks.trading.model.VendorRegisterPageList;
import com.techvg.vks.trading.repository.VendorRegisterRepository;
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
public class VendorRegisterServiceImpl implements VendorRegisterService {
	
	  private final VendorRegisterRepository vendorRegisterRepository;
	  private final VendorRegisterMapper vendorRegisterMapper;

	@Override
	public VendorRegisterDto addVendorDetails(VendorRegisterDto vendorRegisterDto, Authentication authentication) {

		Optional<VendorRegister> vendorOptional = vendorRegisterRepository.findByGstNumber(vendorRegisterDto.getGstNumber());
		if (vendorOptional.isPresent()){
			throw new AlreadyExitsException("VendorRegister already exists for GST Number : " + vendorRegisterDto.getGstNumber());
		}
		else {
			VendorRegister vendorRegister = vendorRegisterMapper.toDomain(vendorRegisterDto);

			vendorRegisterRepository.save(vendorRegister);

			return vendorRegisterMapper.toDto(vendorRegister);
		}
	}

	@Override
	public VendorRegisterDto updateVendorDetails(Long vendorId, VendorRegisterDto vendorRegisterDto) {

		vendorRegisterRepository.findById(vendorId).orElseThrow(
				() -> new NotFoundException("No Vendor found for Id : " +vendorId));
		VendorRegister vendorRegister = vendorRegisterMapper.toDomain(vendorRegisterDto);
		vendorRegister.setId(vendorId);
		vendorRegisterRepository.save(vendorRegister);

		return vendorRegisterMapper.toDto(vendorRegister);

	}

	@Override
	public VendorRegisterDto getVendorById(Long vendorId) {
		VendorRegister vendorRegister = vendorRegisterRepository.findById(vendorId).orElseThrow(
				() -> new NotFoundException("No Vendor found for Id : " +vendorId));
		return vendorRegisterMapper.toDto(vendorRegister);
	}

	@Override
	public VendorRegisterDto deleteVendorById(Long vendorId) {
		VendorRegister vendorRegister = vendorRegisterRepository.findById(vendorId).orElseThrow(
				() -> new NotFoundException("No Vendor found for Id : " +vendorId));
		if (vendorRegister != null) {
			vendorRegister.setIsDeleted(true);
			vendorRegisterRepository.save(vendorRegister);
		}
		return vendorRegisterMapper.toDto(vendorRegister);
	}

	@Override
	public VendorRegisterPageList listVendors(Pageable pageable) {
		Page<VendorRegister> vendorPage;
		vendorPage = vendorRegisterRepository.findAll(pageable);

		return new VendorRegisterPageList(vendorPage
				.getContent()
				.stream()
				.map(vendorRegisterMapper::toDto)
				.collect(Collectors.toList()),
				PageRequest
						.of(vendorPage.getPageable().getPageNumber(),
								vendorPage.getPageable().getPageSize()),
				vendorPage.getTotalElements());
	}

	@Override
	public List<VendorRegisterDto> listAllVendor() {
		return vendorRegisterMapper.domainToDtoList(vendorRegisterRepository.findByisDeleted(false));
	}

}
