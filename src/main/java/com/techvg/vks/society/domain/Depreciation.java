package com.techvg.vks.society.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.trading.domain.PurchaseOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="depreciation")
public class Depreciation extends BaseEntity<String>{
		
		@Column(name = "year")
		Integer year;
		
		@Column(name = "written_down_value")
		Double wdv ;
		
		@Column(name = "depreciation")
		Double depreciation ;
		
		@Column(name = "book_value")
		Double bookValue;
		
		@Column(name = "wdv_sold_asset")
		Double wdvSoldAsset; 

		@EqualsAndHashCode.Exclude
	    @ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
		@JoinColumn(name="assets_reg_id")
		public AssetsRegistration assetsReg;
		


}
