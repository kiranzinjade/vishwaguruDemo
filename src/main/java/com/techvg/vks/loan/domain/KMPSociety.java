package com.techvg.vks.loan.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kmp_society")
public class KMPSociety extends BaseEntity<String> {

    @Column(name = "kmp_year")
    private Integer year;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="kmp_id")
    private KMP kmp;

}
