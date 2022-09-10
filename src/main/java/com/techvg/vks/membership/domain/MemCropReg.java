package com.techvg.vks.membership.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.society.domain.CropRegistration;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member_crop_registration")
public class MemCropReg extends BaseEntity<String> {

    @Column(name = "year")
    Integer year;

    @Column(name = "season")
    String season;

    @Column(name="land_type")
    String landType;

    @Column(name="land_gatno")
    String landGatno;

    @Column(name="land_area_hector")
    Integer landAreaHector;

    @Column(name="land_area_r")
    Integer landAreaR;
    
    @Column(name = "kmp_status")
	Boolean kmpStatus = false;

	@Column(name = "society_kmp_status")
	Boolean societyKmpStatus = false;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name="member_id",nullable=false)
    public Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crop_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public CropRegistration crop;
}
