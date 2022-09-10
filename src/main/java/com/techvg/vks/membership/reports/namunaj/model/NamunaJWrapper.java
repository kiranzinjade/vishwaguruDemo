package com.techvg.vks.membership.reports.namunaj.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NamunaJWrapper {
	private java.util.Date classificationDate; 
	private String email;
	private String address;
	private String name;
}
