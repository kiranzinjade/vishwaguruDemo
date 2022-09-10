package com.techvg.vks.accounts.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "voucher_trans")
public class VoucherTrans extends BaseEntity<String> {

    @Column(name = "debit_amt")
    Double debitAmt;

    @Column(name = "credit_amt")
    Double creditAmt;

    @Column(name = "remark")
    String remark;

    @Column(name = "trans_date")
    Date transDate;

    @Column(name = "voucher_no")
    Long voucherNo;

    @Column(name = "balance")
    Double balance;

    @Column(name = "mode")
    String mode; // Cash or Transfer/Cheque

    @Column(name = "trans_type") //debit or credit
    String transType;

    @Column(name = "dayBook_created")
    boolean dayBookCreated;
    
    @ManyToOne
    @JoinColumn(name = "ledger_account_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public LedgerAccounts ledgerAccounts;

}
