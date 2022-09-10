package com.techvg.vks.loan.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Lob;

import org.springframework.web.multipart.MultipartFile;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoanDocumentDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;
    
   	String documentFileName;
   	String FileName;
  //private MultipartFile documentFile[];
	private byte[] documentFile;
		
@Builder	
public LoanDocumentDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
		String lastModifiedBy, Boolean isDeleted, String documentFileName, String fileName, byte[] documentFile) {
	super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
	this.documentFileName = documentFileName;
	FileName = fileName;
	this.documentFile = documentFile;
}
	
	
	
    
}
