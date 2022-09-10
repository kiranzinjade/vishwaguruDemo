package com.techvg.vks.loan.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.society.domain.CropRegistration;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kmp_crop")
public class KMPCrop extends BaseEntity<String> {

    @Column(name = "season")
    String season;

    @Column(name = "year")
    Integer year;

    @Column(name = "tharav_no")
    String tharavNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crop_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public CropRegistration crop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kmp_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public KMP kmp;
}
