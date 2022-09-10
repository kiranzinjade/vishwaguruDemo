package com.techvg.vks.membership.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "document")
public class Document extends BaseEntity<String> {
	
	@Lob
	@Column(name = "photo")
	private byte[] photo;

	@Lob
	@Column(name = "signature")
	private byte[] signature;

	@Column(name = "photo_file_name")
	String photoFileName;

	@Column(name = "signature_file_name")
	String signatureFileName;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	public Member member;

	
}
