
package com.techvg.vks.membership.controller;

import com.techvg.vks.membership.model.DocumentDto;
import com.techvg.vks.membership.model.DocumentPageList;
import com.techvg.vks.membership.model.MemberDto;
import com.techvg.vks.membership.model.MemberPageList;
import com.techvg.vks.membership.reports.MemberwiseShareReport.MemberwiseShareReportWrapper;
import com.techvg.vks.membership.reports.MemberwiseShareReport.MemberwiseShareWrapperPageList;
import com.techvg.vks.membership.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/member")
public class MemberController {
	
 private final MemberService memberService;
	 
	 
	 @PostMapping
	 public ResponseEntity<MemberDto> addNewMember(@RequestBody MemberDto member ) {
			log.debug("REST request to save Members details : {}", member);
			return new ResponseEntity<>( memberService.addNewMember(member), HttpStatus.CREATED);
		}
		
		@PostMapping(value={"/addPhoto/{memberId}"}, produces = { MediaType.APPLICATION_JSON_VALUE })
	 public ResponseEntity<MemberDto> addPhotoSignature(@PathVariable("memberId") Long memberId ,@RequestPart("member") MemberDto member,@RequestPart("photo") MultipartFile photo, @RequestPart("signature") MultipartFile signature) {
			log.debug("REST request to save Members details : {}", memberId);
			return new ResponseEntity<>( memberService.addPhotoSignature(memberId,member,photo,signature), HttpStatus.CREATED);
		}
	 
	@GetMapping
	public ResponseEntity<MemberPageList> listAllMember(Pageable pageable) {

		log.debug("REST request to get a all records of Members");
		MemberPageList memberList = memberService.listMembers(pageable);
		return new ResponseEntity<>(memberList, HttpStatus.OK);
	}

	@GetMapping( "/depositMemberList")
	public ResponseEntity<List<MemberDto>> listDepositMembers() {
		return new ResponseEntity<>(memberService.listMembersWithDeposits(), HttpStatus.OK);
	}
	
	@PutMapping(value="/{memberId}",produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MemberDto> updateMember(@PathVariable("memberId") Long memberId,@RequestPart("member") MemberDto memberDto,Authentication authentication) {
		log.debug("REST request to update Member  information : {}", memberDto);
		return new ResponseEntity<>(memberService.updateMember(memberId, memberDto,authentication), HttpStatus.OK);
	}
		
	@DeleteMapping(path = { "/{memberId}" }) // deleting data
	public ResponseEntity<MemberDto> deleteMemberById(@PathVariable("memberId") Long memberId) {
		
		return new ResponseEntity<>(memberService.deleteMemberById(memberId), HttpStatus.OK);
	}

	@GetMapping({ "/{memberId}" })
		public MemberDto findById(@PathVariable("memberId") Long memberId) {
			 log.debug("Request to get Member By Id : {}",memberId);     
			return memberService.findById(memberId);
	}
	
	@GetMapping({ "/document/{memberId}" })
	public DocumentDto findDocumentById(@PathVariable("memberId") Long memberId) {
		 log.debug("Request to get Member By Id : {}",memberId);     
		 return memberService.findDocumentByMemberId(memberId);
}
		
	 @PostMapping(value ="/{memberId}",consumes = {"multipart/form-data"})
		public ResponseEntity<DocumentDto> addDocument(@PathVariable("memberId") Long memberId, @RequestParam("photo") MultipartFile photo,@RequestParam("signature") MultipartFile signature) throws IOException {
			log.debug("REST request to save Document details : {}", photo,signature,memberId);
			return new ResponseEntity<>(memberService.addDocument(memberId,photo,signature), HttpStatus.CREATED);
		}


	 
	 
		@GetMapping(value="/get") 
		public ResponseEntity<DocumentPageList> listAllDocumnet(Pageable pageable) {

			log.debug("REST request to get a all records of Documents");
			DocumentPageList documentPageList = memberService.listDocuments(pageable);
			return new ResponseEntity<>(documentPageList, HttpStatus.OK);
		}

	 @DeleteMapping(path = { "/get/{documentId}" }) // deleting data
		public DocumentDto delete(@PathVariable("documentId") Long documentId) {
			 log.debug("Request to delete Document Details : {}",documentId);  
			return memberService.deleteById(documentId);
		}
	 
	  @GetMapping("/export/memberList.csv")
	  public void downloadCSV(HttpServletResponse response) throws IOException{
	    response.setContentType("text/csv");
	    response.setHeader("Content-Disposition","attachment; file= memberList.csv");
	      memberService.downloadCsvfile(response.getWriter());      
	  }
	  
	  @GetMapping({ "/shares/{memberId}" })
		public MemberDto closeMembership(@PathVariable("memberId") Long memberId) {
			 log.debug("Request to get Member By Id : {}",memberId);     
			return memberService.closeMembership(memberId);
	}

	@GetMapping( "/sharememberlist")
	public ResponseEntity<List<MemberwiseShareReportWrapper>> listMembersShares() {
		return new ResponseEntity<>(memberService.listMemberShares(), HttpStatus.OK);
	}
	

	@GetMapping( "/memberlist")
	public ResponseEntity<List<MemberDto>> memberList() {
		return new ResponseEntity<>(memberService.memberList(), HttpStatus.OK);
	}
	

	@GetMapping( "/sharememberpagelist")
	public ResponseEntity<MemberwiseShareWrapperPageList> listMembersSharesPage(Pageable pageable) {
		return new ResponseEntity<>(memberService.listMemberSharesPage(pageable), HttpStatus.OK);
	}
	
	@GetMapping( "/count")
	public int getMembersCount() {
		return memberService.getMembersCount();
	}
	

}


	


