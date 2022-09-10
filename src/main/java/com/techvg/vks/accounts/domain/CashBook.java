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
@Table(name = "cashbook")
public class CashBook  extends BaseEntity<String>  {

    @Column(name = "debit_amt")
    Double debitAmt;

    @Column(name = "credit_amt")
    Double creditAmt;

    @Column(name = "particulars")
    String particulars;

    @Column(name = "trans_date")
    Date transDate;

    @Column(name = "voucher_no")
    String voucherNo;

    @Column(name="trans_type")
    String transType;

    @Column(name = "balance")
    Double balance;

    @Column(name = "authorised_by")
    String authorisedBy;
}
