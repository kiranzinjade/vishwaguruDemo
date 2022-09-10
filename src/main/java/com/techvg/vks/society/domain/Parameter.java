package com.techvg.vks.society.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.techvg.vks.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="parameter_lookup")
public class Parameter extends BaseEntity<String>{

	String value;
	String type;

}
