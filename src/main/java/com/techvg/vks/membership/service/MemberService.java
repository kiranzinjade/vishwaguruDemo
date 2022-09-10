package com.techvg.vks.membership.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.techvg.vks.membership.reports.MemberwiseShareReport.MemberwiseShareReportWrapper;
import com.techvg.vks.membership.reports.MemberwiseShareReport.MemberwiseShareWrapperPageList;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techvg.vks.membership.model.DocumentDto;
import com.techvg.vks.membership.model.DocumentPageList;
import com.techvg.vks.membership.model.MemberDto;
import com.techvg.vks.membership.model.MemberPageList;


@Service
public interface MemberService {
		
	MemberDto addNewMember(MemberDto memberDto);
	MemberDto addPhotoSignature(Long memberId ,MemberDto member,MultipartFile photo, MultipartFile signature);
		
	MemberDto updateMember(Long memberId, MemberDto memberDto,Authentication authentication);

	public	MemberDto findById(Long memberId);

	MemberDto deleteMemberById(Long memberId);

	MemberPageList listMembers(Pageable pageable);

	DocumentDto addDocument(Long memberId,MultipartFile photo, MultipartFile signature) throws IOException;

	DocumentDto updateDocument(Long memberId, MultipartFile photo, MultipartFile signature) throws IOException;

	DocumentDto deleteById(Long documentId);
	
	DocumentDto findDocumentByMemberId(Long memberId);

	DocumentPageList listDocuments(Pageable pageable);

	void downloadCsvfile(PrintWriter writer);

	List<MemberDto> listMembers();

	List<MemberDto> listMembersWithDeposits();

	MemberDto closeMembership(Long memberId);

	List<MemberwiseShareReportWrapper> listMemberShares();

	List<MemberDto> memberList();

	MemberwiseShareWrapperPageList listMemberSharesPage(Pageable pageable);
	int getMembersCount();

}
