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
@Table(name = "society_config")
public class SocietyConfiguration extends BaseEntity<String> {

	@Column(name = "config_name")
	String configName;

	@Column(name = "config_type")
	String configType;

	@Column(name = "value")
	Double value;

	@Column(name = "year")
	Integer year;

	@Column(name = "status")
	String status;

}
