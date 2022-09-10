package com.techvg.vks.loan.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class LoanDemandCountDto implements Serializable {
	private static final long serialVersionUID = -4067460774728860891L;

	private Long id;
	private String cropName;
	private String season;
	private Integer year;
	private Long memberCount;

	public LoanDemandCountDto(Long id, String cropName, String season,
							  Integer year, Long memberCount) {
		this.id = id;
		this.season = season;
		this.year = year;
		this.memberCount = memberCount;
		this.cropName = cropName;
		}
}



	
	
	 
	
	 
	 
	 


