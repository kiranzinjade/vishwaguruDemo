package com.techvg.vks.loan.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="npa_data")
public class NPAData extends BaseEntity<String> {

    @Column(name = "npa_date")
    Date npaDate;

    @Column(name = "loan_type")
    String loanType;

    @Column(name = "standard_amount")
    Double standardAmt;

    @Column(name = "standard_cnt")
    Integer standardCnt;

    @Column(name = "standard_provision")
    Double standardProvision;

    @Column(name = "substandard_amount")
    Double substandardAmt;

    @Column(name = "substandard_cnt")
    Integer substandardCnt;

    @Column(name = "substandard_provision")
    Double substandardProvision;

    @Column(name = "doubtful1_amount")
    Double doubtful1Amt;

    @Column(name = "doubtful1_cnt")
    Integer doubtful1Cnt;

    @Column(name = "doubtful1_provision")
    Double doubtful1Provision;

    @Column(name = "doubtful2_amount")
    Double doubtful2Amt;

    @Column(name = "doubtful2_cnt")
    Integer doubtful2Cnt;

    @Column(name = "doubtful2_provision")
    Double doubtful2Provision;

    @Column(name = "doubtful3_amount")
    Double doubtful3Amt;

    @Column(name = "doubtful3_cnt")
    Integer doubtful3Cnt;

    @Column(name = "doubtful3_provision")
    Double doubtful3Provision;

    @Column(name = "loss_amount")
    Double lossAmt;

    @Column(name = "loss_cnt")
    Integer lossCnt;

}
