package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="society_prerequisite")
public class Prerequisites extends BaseEntity<String> {

	@Column(name = "document_name")
	String documentName;
	
	@Column(name = "document_desc")
	String documentDesc;

	@Column(name = "doc_type")
	String docType;

	@Column(name="mandatory")
	String mandatory;
	
	@Column(name="loan_type")
	String loanType;

}
