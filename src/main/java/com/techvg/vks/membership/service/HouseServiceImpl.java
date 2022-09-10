package com.techvg.vks.membership.service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.House;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.mapper.HouseMapper;
import com.techvg.vks.membership.mapper.MemberMapper;
import com.techvg.vks.membership.model.HouseDto;
import com.techvg.vks.membership.model.HousePageList;
import com.techvg.vks.membership.repository.HouseRepository;
import com.techvg.vks.membership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseServiceImpl implements HouseService {

	private final HouseRepository houseRepository;
	private final HouseMapper houseMapper;
	private final MemberRepository memberRepository;
   	private final MemberMapper memberMapper;

	@Override
	public Set<HouseDto> addNewHouse(Long memberId, HouseDto houseDto) {

		Member member = memberRepository.findById(memberId).orElseThrow(
				() -> new NotFoundException("No member  found for id : " +memberId));
		Optional<House> houseObjOptional = houseRepository.findByHouseNumber(houseDto.getHouseNumber());

		if (houseObjOptional.isPresent()) {
			throw new AlreadyExitsException(
					"House already exists with the House Number : " + houseDto.getHouseNumber());
		} else {
			houseDto.setStatus("A");
			House house = this.houseMapper.houseDtoToHouse(houseDto);
			// house.setMember(member);
			member.getHouse().add(house);
			memberRepository.save(member);
			return (memberMapper.memberToMemberDto(member)).getHouseDtoSet();
		}
	}

	@Override
	public HouseDto updateHouse(Long houseId, HouseDto houseDto, Authentication authentication) {
		houseRepository.findById(houseId).orElseThrow(
				() -> new NotFoundException("No House found for id : " +houseId));
		houseDto.setStatus("A");
		House house = houseMapper.houseDtoToHouse(houseDto);
		house.setId(houseId);
		return houseMapper.houseToHouseDto(houseRepository.save(house));

	}

	@Override
	public HouseDto deleteHouseById(Long houseId) {
		House house = houseRepository.findById(houseId).orElseThrow(
				() -> new NotFoundException("No House found for id : " +houseId));
		if (house != null) {
			house.setStatus("I");
			houseRepository.save(house);
		}
		return houseMapper.houseToHouseDto(house);
	}

	@Override
	public HousePageList listHouse(Pageable pageable) {
		log.debug("Request to get House : {}");

		Page<House> housePage;
		housePage = houseRepository.findByStatus("A", pageable);

		return new HousePageList(
				housePage.getContent().stream().map(houseMapper::houseToHouseDto).collect(Collectors.toList()),
				PageRequest.of(housePage.getPageable().getPageNumber(), housePage.getPageable().getPageSize()),
				housePage.getTotalElements());
	}

	@Override
	public HouseDto getHouseById(Long houseId) {
		log.debug("REST request to getHouse : {}", houseId);
		House house = houseRepository.findById(houseId)
				.orElseThrow(() -> new NotFoundException("No House found for houseId : " + houseId));

		return houseMapper.houseToHouseDto(house);
	}

}
