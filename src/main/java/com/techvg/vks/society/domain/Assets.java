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
@Table(name = "assets")
public class Assets extends BaseEntity<String>{

	@Column(name = "assets_name")
	String assetsName;

	@Column(name = "assets_type")
	String assetsType;

	@Column(name = "catagory")
	String catagory;
	
	@Column(name = "depreciation")
	Double depreciation;
}
