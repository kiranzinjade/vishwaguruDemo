package com.techvg.vks.society.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.society.model.ExecutiveMembersDto;
import com.techvg.vks.society.model.ExecutivePageList;

@Service
public interface ExecutiveMembersService {

	public ExecutiveMembersDto deleteExecutiveMember(Long id);

	public ExecutiveMembersDto updateExecutiveMember(Long id,ExecutiveMembersDto electedmembers);

	public ExecutiveMembersDto readExecutiveMember(Long id);

	public ExecutivePageList getAllExecutiveMembers(Pageable page);

	public ExecutiveMembersDto addSocietyInvestment(ExecutiveMembersDto executiveMembers, Authentication authentication);
}
