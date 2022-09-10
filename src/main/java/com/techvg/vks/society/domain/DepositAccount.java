package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.deposit.domain.Deposit;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deposit_account")
public class DepositAccount extends BaseEntity<String> {

    @Column(name = "name")
    String name;

    @Column(name = "interest")
    Double interest;

    @Column(name = "fd_duration_days")
    Integer fdDurationDays;

    @Column(name = "rd_duration_months")
    Integer rdDurationMonths;

    
    @Column(name = "lock_in_period")
    Integer lockInPeriod;

    @Column(name = "reinvest_interest")
    Boolean reinvestInterest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deposit_type_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public DepositType depositType;
    
    @EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "depositAccount", cascade = CascadeType.ALL)
	private Set<Deposit> deposit;
}
