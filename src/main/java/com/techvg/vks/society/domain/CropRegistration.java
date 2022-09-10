package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.loan.domain.CropLoanDemand;
import com.techvg.vks.loan.domain.LoanDemand;
import com.techvg.vks.membership.domain.MemCropReg;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="crop_registration")

public class CropRegistration extends BaseEntity<String>{
	
	@Column(name = "season")
	String season;
	
	@Column(name = "crop_name")
	String cropName;
	
	@Column(name = "month_duration")
	Integer monthDuration;	

	@Column(name="crop_limit")
	Double cropLimit;
	
	@Column(name = "year")
	Integer year;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch = FetchType.LAZY,mappedBy="crop",cascade=CascadeType.ALL)
	private Set<LoanDemand> loanDemand;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch = FetchType.LAZY,mappedBy="crop",cascade=CascadeType.ALL)
	private Set<CropLoanDemand> cropLoanDemands;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch = FetchType.LAZY,mappedBy="crop",cascade=CascadeType.ALL)
	private Set<MemCropReg> memCropRegs;
}
