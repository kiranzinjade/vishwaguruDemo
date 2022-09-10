package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "society_investment_details")
public class SocietyInvestmentDetails extends BaseEntity<String> {

    @Column(name = "deposit_date")
    Date depositDate;

    @Column(name = "particulars")
    String particulars;

    @Column(name="debit_amount")
    Double debitAmount;

    @Column(name="credit_amount")
    Double creditAmount;

    @Column(name="balance_amount")
    Double balanceAmount;

    @Column(name="interest_amount")
    Double interestAmount;

    @Column(name = "interest_date")
    Date interestDate;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public SocietyInvestment societyInvestment;

}
