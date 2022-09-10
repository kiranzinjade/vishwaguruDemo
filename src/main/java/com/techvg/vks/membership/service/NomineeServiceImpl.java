
package com.techvg.vks.membership.service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.domain.Nominee;
import com.techvg.vks.membership.mapper.NomineeMapper;
import com.techvg.vks.membership.model.NomineeDto;
import com.techvg.vks.membership.model.NomineePageList;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.membership.repository.NomineeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NomineeServiceImpl implements NomineeService{

	private final NomineeRepository nomineeRepository;
	private final MemberRepository memberRepository;
	private final NomineeMapper nomineeMapper;

	@Override
	public NomineeDto addNominee(List<NomineeDto> nomineeDtoList, Long memberId,Authentication authentication) {
				
		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);
		
		  Nominee nominee=null;
		
		   for(NomineeDto nom:nomineeDtoList) {
	  		nominee = this.nomineeMapper.nomineeDtoToNominee(nom);
			nominee.setMember(member);
			nomineeRepository.save(nominee);
		   }
   		return null;
	}

	
	@Override
	 public NomineePageList listNominees(Pageable pageable) {
		log.debug("Request to get nominee : {}");

		Page<Nominee> nomineePage;
		nomineePage = nomineeRepository.findByIsDeleted(false,pageable);

		return new NomineePageList(nomineePage
										.getContent()
										.stream()
										.map(nomineeMapper::nomineeToNomineeDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(nomineePage.getPageable().getPageNumber(),
													nomineePage.getPageable().getPageSize()),
											nomineePage.getTotalElements());

	}

	@Override
	public NomineeDto updateNominee(List<NomineeDto> nomineeDto, Long memberId,
			Authentication authentication) {

				Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);
				Nominee nominee=null;
			
				for(NomineeDto nom:nomineeDto) {
					nominee = this.nomineeMapper.nomineeDtoToNominee(nom);
			  		nominee.setId(nom.getId());
					nominee.setMember(member);
					nomineeRepository.save(nominee);
				};
			return null;
	}
	
	@Override
	public NomineeDto delete(Long nomineeId) {
			
			Nominee nominee = nomineeRepository.findById(nomineeId).orElseThrow(NotFoundException::new);
			if (nominee != null) {
				nominee.setIsDeleted(true);
				nomineeRepository.save(nominee);
			}
		return nomineeMapper.nomineeToNomineeDto(nominee);
	}


	@Override
	public List<NomineeDto> getNomineeById( Long memberId) {
		log.debug("REST request to get Product Type : {}", memberId);
		List<Nominee> nominee = nomineeRepository.findByMemberId(memberId);
		
		
		return nomineeMapper.domainToDtoList(nominee);
		//return 
	}

}

