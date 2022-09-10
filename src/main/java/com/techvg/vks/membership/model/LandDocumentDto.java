package com.techvg.vks.membership.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import com.techvg.vks.base.BaseEntityDto;

@Data
@NoArgsConstructor
public class LandDocumentDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;
	
	private byte[] saatBara;

	private byte[] eightA;

	private byte[] jindagiPatrak;

	 private String saatBarafileName;

	 private String eightAfileName;
    
	 private String jindagiPatrakfileName;

	Long landId;

	@Builder 
	public LandDocumentDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted, byte[] saatBara, byte[] eightA, byte[] jindagiPatrak,
			String saatBarafileName, String eightAfileName, String jindagiPatrakfileName, Long landId) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.saatBara = saatBara;
		this.eightA = eightA;
		this.jindagiPatrak = jindagiPatrak;
		this.saatBarafileName = saatBarafileName;
		this.eightAfileName = eightAfileName;
		this.jindagiPatrakfileName = jindagiPatrakfileName;
		this.landId = landId;
	}
	 
	 
	 
	 
}
