package com.techvg.vks.membership.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="land_documents")
public class LandDocument extends BaseEntity<String>{
	
	@Lob
	private byte[] saatBara;
	@Lob
	private byte[] eightA;
	@Lob
	private byte[] jindagiPatrak;

	@Column(name="saat_barafile_name")
	String saatBarafileName;
	
	@Column(name="eight_afile_name")
	String eightAfileName;
	
	@Column(name="jindagi_patrakfile_name")
	String jindagiPatrakfileName;
	
	@Column(name = "land_id")
	Long landId;

}
