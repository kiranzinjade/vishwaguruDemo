package com.techvg.vks.society.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.Null;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor

public class SocietyDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;
    
    String societyName;
    String address;
    String village;
    String tahsil;
    String district;
    String state;
    String registrationNumber;
    String gstinNumber;
    String panNumber;
    String tanNumber;
    int phoneNumber;
    String emailId;
    int pinCode;
    String societyId;
    @Lob
	private byte[] imgFile;
    String fileName;
    @Builder
	public SocietyDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, String societyName, String address, String village, String tahsil,
			String district, String state, String registrationNumber, String gstinNumber, String panNumber,
			String tanNumber, int phoneNumber, String emailId, int pinCode, String societyId, byte[] imgFile,
			String fileName) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.societyName = societyName;
		this.address = address;
		this.village = village;
		this.tahsil = tahsil;
		this.district = district;
		this.state = state;
		this.registrationNumber = registrationNumber;
		this.gstinNumber = gstinNumber;
		this.panNumber = panNumber;
		this.tanNumber = tanNumber;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.pinCode = pinCode;
		this.societyId = societyId;
		this.imgFile = imgFile;
		this.fileName = fileName;
	}

    
   


}