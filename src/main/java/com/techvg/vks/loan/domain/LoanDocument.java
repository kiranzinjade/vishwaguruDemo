package com.techvg.vks.loan.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan_document")
public class LoanDocument extends BaseEntity<String> {
	
	
	@Column(name = "documentfile_name")
	String documentFileName;
	
	@Column(name = "file_name")
	String FileName;
	
	@Lob
	@Column(name = "document_file")
	private byte[] documentFile;
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "loan_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public LoanDetails loanDetails;
 
	

}
