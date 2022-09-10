package com.techvg.vks.membership.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.techvg.vks.base.BaseEntityDto;
import com.techvg.vks.membership.model.MemberDto;
@JsonAutoDetect

@Data
@NoArgsConstructor
public class NomineeDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;
 

	 @NotBlank
	 private String firstName;
	 
	 @NotBlank
	 private String middleName;
	
	 @NotBlank
	 private String lastName;
	
	 private String fatherName;
	
	 private String motherName;
	 
	 private String guardianName;
	
	 @NotBlank
	 private String gender;
	
	 @NotNull
	 private Date dob;
	
	 @NotBlank
	 private String aadharCard;
	
	 @NotNull
	 private Date nomineeDeclareDate;
	
	 private String nomineeRelation;
	
	 private String nomineePermanentAddr;

	 private String parentPermanentAddr;

	 @Builder
	 public NomineeDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted,@NotBlank String firstName, @NotBlank String middleName, @NotBlank String lastName,
				String fatherName, String motherName, @NotBlank String gender, @NotNull Date dob,
				@NotBlank String aadharCard, @NotNull Date nomineeDeclareDate, String nomineeRelation,
				String nomineePermanentAddr, String parentPermanentAddr,String guardianName) {
			super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
			this.firstName = firstName;
			this.middleName = middleName;
			this.lastName = lastName;
			this.fatherName = fatherName;
			this.motherName = motherName;
			this.gender = gender;
			this.dob = dob;
			this.aadharCard = aadharCard;
			this.nomineeDeclareDate = nomineeDeclareDate;
			this.nomineeRelation = nomineeRelation;
			this.nomineePermanentAddr = nomineePermanentAddr;
			this.parentPermanentAddr = parentPermanentAddr;
			this.guardianName=guardianName;
		}

	
	 
}
