package com.techvg.vks.deposit.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deposit_accural")
public class DepositAccrual extends BaseEntity<String> {

    @Column(name = "accrual_date")
    LocalDate accrualDate;

    @Column(name = "period_from")
    LocalDate periodFrom;

    @Column(name = "period_to")
    LocalDate periodTo;

    @Column(name = "interest_accrued")
    Double interestAccrued;

    @Column(name = "debit")
    Double debit;

    @Column(name = "credit")
    Double credit;

    @Column(name = "account_no")
    Long accountNo;

    @Column(name = "parent_acc_head")
    String parentAccHead;

    @Column(name = "posted")
    Boolean isPosted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public Deposit deposit;
}
