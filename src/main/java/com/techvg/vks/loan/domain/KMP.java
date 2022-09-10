package com.techvg.vks.loan.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kmp")
public class KMP extends BaseEntity<String> {

    @Column(name = "year")
    Integer year;

    @Column(name = "kmp_generation_status")
    Boolean kmpGenerationStatus = false;

    @Column(name = "kmp_approval_status")
    Boolean kmpApprovalStatus = false;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,mappedBy="kmp",cascade=CascadeType.ALL)
    private Set<KMPMember> kmpMembers=new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,mappedBy="kmp",cascade=CascadeType.ALL)
    private Set<KMPCrop> kmpCrops=new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy="kmp", fetch= FetchType.LAZY)
    private KMPSociety kmpSociety;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,mappedBy="kmp",cascade=CascadeType.ALL)
    private Set<CropLoanDemand> cropLoanDemands;
}
