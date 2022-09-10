package com.techvg.vks.membership.model;


import com.techvg.vks.base.BaseEntityDto;
import com.techvg.vks.loan.model.LoanDemandDto;
import com.techvg.vks.loan.model.LoanDetailsDto;
import com.techvg.vks.share.model.SharesAllocationDto;
import com.techvg.vks.share.model.SharesDto;
import com.techvg.vks.society.model.AgmAttendanceDto;
import com.techvg.vks.trading.model.SalesRegisterDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;


@Data

@NoArgsConstructor
@AllArgsConstructor
public class MemberDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = -7842093274903322374L;
	

	    private String memberUniqueId;
		
		@NotBlank
	    private  String firstName;
		
		@NotBlank
		private String middleName;
		
		@NotBlank
		private   String lastName;
		
		@NotBlank
		private	String gender;
		
		private String fatherName;
		
		private String motherName;
		
		@NotNull
		private Date dob;
		
		private String email;
		
		private String phoneNumber;

		private String religion;
		
		private String category;
		
		private String cast;
		
		@NotBlank	
		private String aadharCard;
		
		@NotBlank
		private String panCard;
		
		private String rationCard;
		
		private Integer familyMemberCount;
		
		private String  occupation;
		
		private Integer  dependentsCount;
		
		//@NotNull
		private Date applicationDate;
		
		private String status;
		
		private Integer noOfShare=1;

		private Double registrationFee=0.0;

		private String boardResolutionNoAndDate;

		private Integer totalAllocatedShares;
		
		private Set<NomineeDto> nomineeDtoSet;
		private Set<SharesDto> sharesDtoSet;
		private Set<SalesRegisterDto> salesRegisterDto;
		private Set<SharesAllocationDto> sharesAllocationDtoSet;
		
		private DocumentDto documentDto;

		private Set<MemberBankDto> memberBankDtoSet;
		
		private Set<MemberBelongingDto> memberBelongingDtoSet;
		                                
		private Set<LandDto> landDtoSet;
        
		private Set<HouseDto> houseDtoSet;
		
		private Set<AgmAttendanceDto> agmAttendanceDtoSet;
		
		private Set<LoanDetailsDto> loanDetailsDtoSet;

		private Set<LoanDemandDto> loanDemandsDtoSet;

		private String memberType;

	    public MemberDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted,String memberUniqueId, @NotBlank String firstName, @NotBlank String middleName,
			@NotBlank String lastName, @NotBlank String gender, String fatherName, String motherName, @NotNull Date dob,
			String email, String phoneNumber, String religion, String category, String cast,
			@NotBlank String aadharCard, @NotBlank String panCard, String rationCard, Integer familyMemberCount,
			String occupation, Integer dependentsCount, String status, Integer noOfShare,
			Double registrationFee, String boardResolutionNoAndDate, Set<NomineeDto> nomineeDtoSet, Set<SharesDto> sharesDtoSet,
			Set<SharesAllocationDto> sharesAllocationDtoSet, DocumentDto documentDto, Set<MemberBankDto> memberBankDtoSet,
			Set<MemberBelongingDto> memberBelongingDtoSet, Set<LandDto> landDtoSet, Set<HouseDto> houseDtoSet,
			Set<AgmAttendanceDto> agmAttendanceDtoSet, Set<LoanDetailsDto> loanDetailsDtoSet, Set<LoanDemandDto> loanDemandsDtoSet,
			Set<SalesRegisterDto> salesRegisterDto, String memberType) {

		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.memberUniqueId = memberUniqueId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.gender = gender;
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.dob = dob;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.religion = religion;
		this.category = category;
		this.cast = cast;
		this.aadharCard = aadharCard;
		this.panCard = panCard;
		this.rationCard = rationCard;
		this.familyMemberCount = familyMemberCount;
		this.occupation = occupation;
		this.dependentsCount = dependentsCount;
		this.applicationDate = applicationDate;
		this.status = status;
		this.nomineeDtoSet = nomineeDtoSet;
		this.sharesDtoSet = sharesDtoSet;
		this.sharesAllocationDtoSet = sharesAllocationDtoSet;
		this.documentDto = documentDto;
		this.memberBankDtoSet = memberBankDtoSet;
		this.memberBelongingDtoSet = memberBelongingDtoSet;
		this.landDtoSet = landDtoSet;
		this.houseDtoSet = houseDtoSet;
		this.agmAttendanceDtoSet = agmAttendanceDtoSet;
		this.loanDetailsDtoSet=loanDetailsDtoSet;
		this.loanDemandsDtoSet = loanDemandsDtoSet;
		this.salesRegisterDto=salesRegisterDto;
		this.noOfShare = noOfShare;
		this.registrationFee = registrationFee;
		this.boardResolutionNoAndDate = boardResolutionNoAndDate;
		this.memberType=memberType;
	}
		
		
}
