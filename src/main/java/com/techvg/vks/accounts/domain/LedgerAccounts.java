package com.techvg.vks.accounts.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ledger_accounts")
public class LedgerAccounts extends BaseEntity<String> {

    @Column(name = "account_no")
    Long accountNo;

    @Column(name = "account_name")
    String accountName;

    @Column(name = "account_balance")
    Double accBalance;

    @Column(name = "acc_head_code") //seed data for top 1 & 2 level ledger accounts
    String accHeadCode;

    @Column(name = "ledger_code")
    String ledgerCode;

    @Column(name = "app_code") //(Loan/Deposit/Shares/Sales/Purchase/Savings)
    String appCode;

    @Column(name = "classification") //(BALANCE SHEET / PROFIT AND LOSS / TRADING)
    String classification;

    @Column(name = "category") //Real / Nominal / Personal
    String category;

    @Column(name = "level")
    Integer level;

    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = { CascadeType.ALL }, fetch= FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true)
    private LedgerAccounts parentLedger;

    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "parentLedger", fetch= FetchType.LAZY)
    private Set<LedgerAccounts> childLedgers = new HashSet<>();

   // @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL,fetch= FetchType.LAZY)
    @JoinColumn(name = "account_type_id",  referencedColumnName = "id")
    private AccountType accountType;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "ledgerAccounts", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<GeneralLedger> generalLedgerAccounts;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "ledgerAccounts", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<VoucherTrans> voucherTransactions;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy="ledgerAccount", fetch= FetchType.LAZY)
    private Set<AccountMapping> accountMapping;
}
