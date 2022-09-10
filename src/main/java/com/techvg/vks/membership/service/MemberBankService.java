package com.techvg.vks.membership.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.techvg.vks.membership.model.MemberBankDto;
import com.techvg.vks.membership.model.MemberBankPageList;

public interface MemberBankService {

	MemberBankDto addNewMemberBank(Long memberId, MemberBankDto memberBankDto);

	MemberBankDto updateMemberBank(Long id, MemberBankDto memberBankDto, Authentication authentication);

	MemberBankDto deleteMemberBank(Long id);

	MemberBankPageList listMemberBank(Pageable pageable);

	MemberBankDto getMemberBankById(Long id);
	MemberBankDto getMemberBankByMember(Long memberid);
}
