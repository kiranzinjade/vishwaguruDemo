package com.techvg.vks.membership.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Null;

import com.techvg.vks.base.BaseEntityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class DocumentDto extends BaseEntityDto implements Serializable {
	
    private static final long serialVersionUID = 8506582894893716606L;
 
	private byte[] photo;
	
	private byte[] signature;
	
	private String photoFileName;
	
	private String signatureFileName;
	
	
	@Builder
	public DocumentDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted,byte[] photo, byte[] signature, String photoFileName, String signatureFileName) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.photo = photo;
		this.signature = signature;
		this.photoFileName = photoFileName;
		this.signatureFileName = signatureFileName;
	}


}
