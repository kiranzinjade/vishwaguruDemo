package com.techvg.vks.membership.service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.MemCropReg;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.mapper.MemCropRegMapper;
import com.techvg.vks.membership.mapper.MemberMapper;
import com.techvg.vks.membership.model.MemCropRegDto;
import com.techvg.vks.membership.model.MemCropRegPageList;
import com.techvg.vks.membership.model.MemberDto;
import com.techvg.vks.membership.repository.MemCropRegRepository;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.society.domain.CropRegistration;
import com.techvg.vks.society.model.CropRegistrationDto;
import com.techvg.vks.society.repository.CropRegistrationRepository;
import com.techvg.vks.society.service.CropRegistrationService;
import com.techvg.vks.trading.domain.PurchaseOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemCropRegServiceImpl implements MemCropRegService {

    private final MemCropRegMapper mapper;
    private final MemCropRegRepository repo;
    private final MemberRepository memberRepo;
    private final MemberMapper memberMapper;
    private final CropRegistrationRepository cropRepo;

    @Override
    public MemCropRegDto registerCrop(MemCropRegDto dto,Authentication authentication) {
    	System.out.println("Member Crop Dto----------"+dto);
 
        Optional<MemCropReg> optionalDomain = repo.findByCropNameAndMemberIdAndSeason(dto.getCropName(), dto.getMemberId(), dto.getSeason(), dto.getYear());
        System.out.println("Season-------------"+optionalDomain);
        if (optionalDomain.isPresent()){
            throw new AlreadyExitsException("Crop already registered for the selected season : " + dto.getCropName());
        }
        else {
        	  MemCropReg memCropReg = mapper.toDomain(dto);
        	  Member member=memberRepo.findById(dto.getMemberId()).orElseThrow(NotFoundException::new);
        	         	  memCropReg.setMember(member);
        	  
        	  CropRegistration crop=cropRepo.findById(dto.getCropId()).orElseThrow(NotFoundException::new);
        	  memCropReg.setCrop(crop);
            return mapper.toDto(repo.save(memCropReg));
        }
    }

    @Override
    public MemCropRegDto updateCropRegistration(Long id, MemCropRegDto dto) {
        repo.findById(id).orElseThrow(
                () -> new NotFoundException("No Member Crop Registration  found for id : " +id));
        MemCropReg memCropReg = mapper.toDomain(dto);
        Member member=memberRepo.findById(dto.getMemberId()).orElseThrow(NotFoundException::new);
        memCropReg.setMember(member);
        CropRegistration crop=cropRepo.findById(dto.getCropId()).orElseThrow(NotFoundException::new);
  	  	memCropReg.setCrop(crop);
        memCropReg.setId(id);
        return mapper.toDto(repo.save(memCropReg));
    }

    @Override
    public List<MemCropRegDto> getMemberCropsForYear(Long memberId, int year) {
        return mapper.toDtoList(repo.findByMember_IdAndYear(memberId, year));
    }

    @Override
    public List<MemCropRegDto> getCropsForYear(int year) {
        return mapper.toDtoList(repo.findByYear(year));
    }

    @Override
    public List<MemCropReg> getMemberCrops(Long memberId, int year) {
        return repo.findByMember_IdAndYear(memberId, year);
    }

    @Override
    public MemCropRegPageList listAllCropsForYear(int year, Pageable pageable) {
    	Page<MemCropReg> memCropRegPage;
        memCropRegPage = repo.findByYear(year, pageable);

        return new MemCropRegPageList(memCropRegPage
                .getContent()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(memCropRegPage.getPageable().getPageNumber(),
                                memCropRegPage.getPageable().getPageSize()),
                memCropRegPage.getTotalElements());
    }

	@Override
	public List<MemCropRegDto> getCropsByCurrentYear(Long memberId ) {
		List<MemCropReg> domainList = repo.findCropsByCurrentYear(memberId);
		return mapper.toDtoList(domainList);
	}

	@Override
	public MemCropRegPageList listAllCropsForCurrentYear(Pageable pageable) {
		Page<MemCropReg> memCropRegPage;
		List<MemCropReg> list = repo.findByCurrentYear();
		memCropRegPage =  new PageImpl<>(list,pageable,list.size());

        return new MemCropRegPageList(memCropRegPage
                .getContent()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(memCropRegPage.getPageable().getPageNumber(),
                                memCropRegPage.getPageable().getPageSize()),
                memCropRegPage.getTotalElements());
	}
	@Override
	public MemCropRegDto deleteCropRegistrationById(Long id) {
		MemCropReg memCropReg = repo.findById(id).orElseThrow(NotFoundException::new);
		if (memCropReg != null) {
			memCropReg.setIsDeleted(true);
			repo.save(memCropReg);
		}
	return mapper.toDto(memCropReg);
}

	@Override
	public List<MemberDto> listAllMember() {
		List<MemberDto> memberList= new ArrayList<>();
		List<Long> memCropRegList = repo.findAllMember();
		for(int i=0; i<=memCropRegList.size();i++) {
		Member member = memberRepo.findById(memCropRegList.get(i)).orElseThrow(NotFoundException::new);	
		memberList.add(memberMapper.memberToMemberDto(member));
		}
		return memberList;
	}
}
