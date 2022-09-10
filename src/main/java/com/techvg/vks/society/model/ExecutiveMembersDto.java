package com.techvg.vks.society.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Builder;

import com.techvg.vks.base.BaseEntityDto;
import com.techvg.vks.membership.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ExecutiveMembersDto extends BaseEntityDto {


	private String designation;

	//private Member member;
	
	private Long memberId;
	
	private String fullName;

	private java.util.Date electedFrom;

	private java.util.Date electedTo;

	
	public ExecutiveMembersDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted,String designation, Long memberId, Date electedFrom, Date electedTo,String fullName) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.designation = designation;
		this.memberId = memberId;
		this.electedFrom = electedFrom;
		this.electedTo = electedTo;
		this.fullName=fullName;
	}		
	
	
}
