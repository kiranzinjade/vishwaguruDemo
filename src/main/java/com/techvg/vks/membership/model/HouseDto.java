package com.techvg.vks.membership.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class HouseDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = 8757973262152162821L;

	@NotBlank
	private String houseNumber;

	private String addressLine1;

	private String addressLine2;

	private String city;

	private String tehsil;

	private String district;

	private String state;

	private String pincode;

	private Boolean isOwned;

	private String status;
	private Integer count;
	private double houseArea;
	private double valuation;
	
	private String addressType;

	@Builder
	public HouseDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy,
			@Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted,
			@NotBlank String houseNumber, String addressLine1, String addressLine2, String city, String tehsil,
			String district, String state, String pincode, Boolean isOwned, String status, Integer count,
			double houseArea, double valuation, String addressType) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.houseNumber = houseNumber;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.tehsil = tehsil;
		this.district = district;
		this.state = state;
		this.pincode = pincode;
		this.isOwned = isOwned;
		this.status = status;
		this.count = count;
		this.houseArea = houseArea;
		this.valuation = valuation;
		this.addressType=addressType;
	}

}
