package com.techvg.vks.society.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InterestShortTermLoanDto  extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;
    
    @NotBlank
    private String criteria;   
    Double centralGov;
    Double stateGov;
    Double distBank; 
    Double self;
    Double afterOneYar;
    
    @Builder
	public InterestShortTermLoanDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, @NotBlank String criteria, Double centralGov, Double stateGov,
			Double distBank, Double self, Double afterOneYar) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.criteria = criteria;
		this.centralGov = centralGov;
		this.stateGov = stateGov;
		this.distBank = distBank;
		this.self = self;
		this.afterOneYar = afterOneYar;
	}
	 
	
	
	
	
	
   
}
