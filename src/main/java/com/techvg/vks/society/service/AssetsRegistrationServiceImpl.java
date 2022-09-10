package com.techvg.vks.society.service;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.accounts.service.VouchersService;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.Assets;
import com.techvg.vks.society.domain.AssetsRegistration;
import com.techvg.vks.society.mapper.AssetsRegistrationMapper;
import com.techvg.vks.society.model.AssetsRegistrationDto;
import com.techvg.vks.society.model.AssetsRegistrationPageList;
import com.techvg.vks.society.repository.AssetsRegistrationRepository;
import com.techvg.vks.society.repository.AssetsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetsRegistrationServiceImpl implements AssetsRegistrationService {

	private final AssetsRegistrationMapper assetsRegistrationMapper;
	private final AssetsRegistrationRepository assetsRegistrationRepository;
	private final AssetsRepository assetsRepository;
	private final VouchersService vouchersService;
	private final DepreciationService depreciationService;

	@Override
	public AssetsRegistrationDto addPurchaseAssets(AssetsRegistrationDto assetsRegistrationDto,
			Authentication authentication) {
		AssetsRegistration assetsRegistration = assetsRegistrationMapper
				.assetsRegistrationDtoToAssetsRegistration(assetsRegistrationDto);

		Assets assets = assetsRepository.findById(assetsRegistrationDto.getAssetsId())
				.orElseThrow(() -> new NotFoundException("No Asset Registration found"));
		assetsRegistration.setAssets(assets);
		assetsRegistration.setDate(new Date());
		log.debug("REST request to save Assets Registration : {}", assetsRegistrationDto);

		Optional<AssetsRegistration> assetsRegistration1 = assetsRegistrationRepository
				.findByLastRecord(assetsRegistrationDto.getAssetsId());
		if (assetsRegistration1.isPresent()) {
			if (assetsRegistrationDto.getTransactionType().equalsIgnoreCase("purchase")) {
				assetsRegistration.setBalanceQuantity(
						assetsRegistration1.get().getBalanceQuantity() + assetsRegistrationDto.getQuantity());
				double newBalance = assetsRegistrationDto.getQuantity() * assetsRegistrationDto.getCost();
				assetsRegistration.setBalanceValue(assetsRegistration1.get().getBalanceValue() + newBalance);
			} else {
				assetsRegistration.setBalanceQuantity(
						assetsRegistration1.get().getBalanceQuantity() - assetsRegistrationDto.getQuantity());
				double newBalance = assetsRegistrationDto.getQuantity() * assetsRegistrationDto.getCost();
				assetsRegistration.setBalanceValue(assetsRegistration1.get().getBalanceValue() - newBalance);
			}

		}else {
			assetsRegistration.setBalanceQuantity(assetsRegistrationDto.getQuantity());
			assetsRegistration.setBalanceValue(assetsRegistrationDto.getQuantity()*assetsRegistrationDto.getCost());
		}
         VouchersDto vouchersDto=assetsRegistrationDto.getVouchersDto();
         vouchersDto.setMode(assetsRegistrationDto.getMode());
         vouchersDto.setNarration(assetsRegistrationDto.getNarration());
         System.out.println("Vouchers Dto-----------------"+vouchersDto);
		 vouchersService.addVouchers(vouchersDto, authentication);
		 
//		 DepreciationDto depreciationDto =assetsRegistrationDto.getDepreciationDto();
//		 depreciationService.addDepreciaton(depreciationDto, authentication);
		 
		 
		return assetsRegistrationMapper.assetsRegistrationToAssetsRegistrationDto(assetsRegistrationRepository.save(assetsRegistration));
	}

	@Override
	public AssetsRegistrationPageList listAssetsRegistration(Pageable pageable) {
		log.debug("Request to get Assets Registration : {}");

		Page<AssetsRegistration> assetsRegistrationPage;
		assetsRegistrationPage = assetsRegistrationRepository.findByisDeleted(pageable, false);

		return new AssetsRegistrationPageList(assetsRegistrationPage.getContent().stream()
				.map(assetsRegistrationMapper::assetsRegistrationToAssetsRegistrationDto).collect(Collectors.toList()),
				PageRequest.of(assetsRegistrationPage.getPageable().getPageNumber(),
						assetsRegistrationPage.getPageable().getPageSize()),
				assetsRegistrationPage.getTotalElements());

	}

	@Override
	public AssetsRegistrationDto deleteAssetsRegistrationById(long id) {
		AssetsRegistration assetsRegistration = assetsRegistrationRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No Asset Registration found for Id " + id));
		if (assetsRegistration != null) {
			assetsRegistration.setIsDeleted(true);
			assetsRegistrationRepository.save(assetsRegistration);
		}
		return assetsRegistrationMapper.assetsRegistrationToAssetsRegistrationDto(assetsRegistration);
	}

	@Override
	public AssetsRegistrationDto updateAssetsRegistration(Long id, AssetsRegistrationDto assetsRegistrationDto) {
		AssetsRegistration assetsRegistration = assetsRegistrationRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No Asset Registration found for Id " + id));
		assetsRegistrationDto.setId(assetsRegistration.getId());
		AssetsRegistration assetsRegistration1 = assetsRegistrationMapper
				.assetsRegistrationDtoToAssetsRegistration(assetsRegistrationDto);
		Assets assets = assetsRepository.findById(assetsRegistrationDto.getAssetsId())
				.orElseThrow(NotFoundException::new);
		assetsRegistration1.setAssets(assets);
		return assetsRegistrationMapper
				.assetsRegistrationToAssetsRegistrationDto(assetsRegistrationRepository.save(assetsRegistration1));
	}

	@Override
	public AssetsRegistrationDto getAssetsRegistrationById(long id) {
		log.debug("REST request to get Assets Registration : {}", id);
		AssetsRegistration assetsRegistration = assetsRegistrationRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No Assets Registration found for Id : " + id));

		return assetsRegistrationMapper.assetsRegistrationToAssetsRegistrationDto(assetsRegistration);
	}
}
