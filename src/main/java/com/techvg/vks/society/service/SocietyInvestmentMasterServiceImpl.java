package com.techvg.vks.society.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.DepositType;
import com.techvg.vks.society.domain.ProductType;
import com.techvg.vks.society.domain.SocietyBank;
import com.techvg.vks.society.domain.SocietyInvestmentMaster;
import com.techvg.vks.society.mapper.SocietyBankMapper;
import com.techvg.vks.society.mapper.SocietyInvestmentMasterMapper;
import com.techvg.vks.society.model.ProductTypePageList;
import com.techvg.vks.society.model.SocietyBankDto;
import com.techvg.vks.society.model.SocietyInvestmentMasterDto;
import com.techvg.vks.society.model.SocietyInvestmentMasterPageList;
import com.techvg.vks.society.repository.DepositTypeRepository;
import com.techvg.vks.society.repository.SocietyBankRepository;
import com.techvg.vks.society.repository.SocietyInvestmentMasterRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocietyInvestmentMasterServiceImpl implements SocietyInvestmentMasterService {

	private final SocietyInvestmentMasterMapper  societyInvestmentMasterMapper;
	private final SocietyInvestmentMasterRepository  societyInvestmentMasterRepository;
	private final SocietyBankRepository  societyBankRepository;
	private final DepositTypeRepository  depositTypeRepository;
	private final SocietyBankMapper societyBankMapper;
	
	@Override
	public SocietyInvestmentMasterDto addSocietyInvestment(SocietyInvestmentMasterDto societyInvestmentMasterDto,
			Authentication authentication) {
		log.debug("REST request to save societyInvestment : {}", societyInvestmentMasterDto);
		SocietyInvestmentMaster  societyInvestmentMaster=societyInvestmentMasterMapper.societyInvestmentMasterDtoToSocietyInvestmentMaster(societyInvestmentMasterDto);
		SocietyBank  societyBank=societyBankRepository.findById(societyInvestmentMasterDto.getBankId()).orElseThrow(NotFoundException::new);
		societyInvestmentMaster.setSocietyBank(societyBank);
		DepositType  depositType=depositTypeRepository.findById(societyInvestmentMasterDto.getDepositTypeId()).orElseThrow(NotFoundException::new);
		societyInvestmentMaster.setDepositType(depositType);
		return societyInvestmentMasterMapper.societyInvestmentMasterToSocietyInvestmentMasterDto(societyInvestmentMasterRepository.save(societyInvestmentMaster));
	
	}
	@Override
	public SocietyInvestmentMasterPageList listSocietyInvestment(Pageable pageable) {
     log.debug("REST request to get Society Investment : {}");
		
		Page<SocietyInvestmentMaster> societyInvestmentPage;
		societyInvestmentPage = societyInvestmentMasterRepository.findByisDeleted(pageable,false);
       
		
		return new SocietyInvestmentMasterPageList(societyInvestmentPage
										.getContent()
										.stream()
										.map(societyInvestmentMasterMapper::societyInvestmentMasterToSocietyInvestmentMasterDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(societyInvestmentPage.getPageable().getPageNumber(),
													societyInvestmentPage.getPageable().getPageSize()),
											societyInvestmentPage.getTotalElements());
	}
	
	@Override
	public SocietyInvestmentMasterDto deleteSocietyInvestmentById(Long id) {
		SocietyInvestmentMaster societyInvestmentMaster = societyInvestmentMasterRepository.findById(id).orElseThrow(NotFoundException::new);
		if (societyInvestmentMaster != null) {
			societyInvestmentMaster.setIsDeleted(true);
			societyInvestmentMasterRepository.save(societyInvestmentMaster);
		}
	return societyInvestmentMasterMapper.societyInvestmentMasterToSocietyInvestmentMasterDto(societyInvestmentMaster);
	}
	
	@Override
	public SocietyInvestmentMasterDto updateSocietyInvestment(Long id, SocietyInvestmentMasterDto societyInvestmentMasterDto) {
		SocietyInvestmentMaster societyInvestmentMaster = societyInvestmentMasterRepository.findById(id).orElseThrow(NotFoundException::new);
		societyInvestmentMasterDto.setId(societyInvestmentMaster.getId());	
		SocietyInvestmentMaster societyInvestmentMaster1 = societyInvestmentMasterMapper.societyInvestmentMasterDtoToSocietyInvestmentMaster(societyInvestmentMasterDto);
		SocietyBank  societyBank=societyBankRepository.findById(societyInvestmentMasterDto.getBankId()).orElseThrow(NotFoundException::new);
		societyInvestmentMaster1.setSocietyBank(societyBank);
		DepositType  depositType=depositTypeRepository.findById(societyInvestmentMasterDto.getDepositTypeId()).orElseThrow(NotFoundException::new);
		societyInvestmentMaster1.setDepositType(depositType);
	
		return societyInvestmentMasterMapper.societyInvestmentMasterToSocietyInvestmentMasterDto(societyInvestmentMasterRepository.save(societyInvestmentMaster1));
	}
	

	@Override
	public SocietyInvestmentMasterDto getSocietyInvestmentById(Long id) {
		log.debug("REST request to get Society Investment : {}", id);
		SocietyInvestmentMaster societyInvestmentMaster = societyInvestmentMasterRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Society Investment Setting found for Id : " +id));

		return societyInvestmentMasterMapper.societyInvestmentMasterToSocietyInvestmentMasterDto(societyInvestmentMaster);
	}
	@Override
	public List<SocietyBankDto> getBankDetailsBysocietyInvestmentId() {
		ArrayList<SocietyBank> domainList=new ArrayList<>();
		
		List<Long> investment = societyInvestmentMasterRepository.findBankBySocietyInvestmentId();
		for (int i=0; i<investment.size(); i++)
		{
			SocietyBank  societyBank=societyBankRepository.findById(investment.get(i)).orElseThrow(NotFoundException::new);
			domainList.add(societyBank);
		}
		return societyBankMapper.domainToDtoList(domainList);
	}
	@Override
	public List<SocietyInvestmentMasterDto> getSchemeBySocietyBankId(Long societyBankId) {
		List<SocietyInvestmentMaster> domainList = societyInvestmentMasterRepository.findSchemeByBankId(societyBankId);
		return societyInvestmentMasterMapper.domainToDtoList(domainList);
	}
}
