package com.techvg.vks.society.model;


import java.time.LocalDateTime;
import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrerequisitesDto extends BaseEntityDto {

	private String documentName;
	
	private String documentDesc;
	
	private String docType;
	
	private String mandatory;
	
	private String loanType;
	
	@Builder
	public PrerequisitesDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted,  String documentName,  String documentDesc,
			 String docType,String mandatory,String loanType) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.documentName = documentName;
		this.documentDesc = documentDesc;
		this.docType = docType;
		this.mandatory=mandatory;
		this.loanType=loanType;
		
}
}