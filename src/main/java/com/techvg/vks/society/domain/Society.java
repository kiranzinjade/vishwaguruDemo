package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sanstha_details")
public class Society extends BaseEntity<String> {

	@Column(name = "society_name")
	String societyName;

	@Column(name = "address")
	String address;

	@Column(name = "village")
	String village;

	@Column(name = "tahsil")
	String tahsil;

	@Column(name = "district")
	String district;

	@Column(name = "state")
	String state;

	@Column(name = "registration_number")
	String registrationNumber;

	@Column(name = "gstin_number")
	String gstinNumber;

	@Column(name = "pan_number")
	String panNumber;

	@Column(name = "tan_number")
	String tanNumber;

	@Column(name = "phone_number")
	int phoneNumber;

	@Column(name = "email_id")
	String emailId;

	@Column(name = "pin_code")
	double pinCode;

	@Column(name = "society_id")
	String societyId;

	@Lob
	@Column(name = "file_data")
	public byte[] fileData;

	@Column(name = "file_name")
	private String fileName;

}
