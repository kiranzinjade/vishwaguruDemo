package com.techvg.vks.accounts.domain;


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
@Table(name = "daybook")
public class DayBook  extends BaseEntity<String> {

    @Column(name = "trans_date")
    Date transDate;

    @Column(name = "particulars")
    String particulars;

    @Column(name = "voucher_count")
    Integer voucherCount;

    @Column(name = "mode") //Cash or Transfer/Cheque
    String mode;

    @Column(name = "trans_type") //debit or credit
    String transType;

    @Column(name = "debit_cash_amt")
    Double debitCashAmt;

    @Column(name = "debit_transfer_amt")
    Double debitTransferAmt;

    @Column(name = "debit_total_amt")
    Double debitTotalAmt;

    @Column(name = "debit_balance")
    Double debitBalance;

    @Column(name = "credit_cash_amt")
    Double creditCashAmt;

    @Column(name = "credit_transfer_amt")
    Double creditTransferAmt;

    @Column(name = "credit_total_amt")
    Double creditTotalAmt;

    @Column(name = "credit_balance")
    Double creditBalance;

    @Column(name = "balance")
    Double balance;

    @Column(name = "authorised_by")
    String authorisedBy;

    @Column(name = "acc_head_code")
    String accHeadCode;

    @Column(name = "is_GL_created")
    boolean isGLCreated;

}
