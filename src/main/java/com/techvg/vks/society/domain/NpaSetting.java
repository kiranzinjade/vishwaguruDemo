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
@Table(name="society_npa_setting")

public class NpaSetting extends BaseEntity<String>{
	

	@Column(name = "duration_start")
	Integer durationStart;
	
	@Column(name="duration_end")
	Integer durationEnd;
	
	@Column(name="provision")
	Double provision;
	
	@Column(name = "classification")
	String classification;

	@Column(name = "year")
	Integer year;

}
