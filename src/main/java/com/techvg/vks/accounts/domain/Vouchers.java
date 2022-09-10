package com.techvg.vks.accounts.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vouchers")
public class Vouchers extends BaseEntity<String> {

    @Column(name = "voucher_no")
    Long voucherNo;

    @Column(name = "voucher_date")
    Date voucherDate;

    @Column(name = "narration")
    String narration;

    @Column(name = "cost_center")
    String costCenter;

    @Column(name = "prepared_by")
    String preparedBy;

    @Column(name = "authorised_by")
    String authorisedBy;

    @Column(name = "mode")
    String mode; // Cash or Transfer/Cheque

    @Column(name = "app_code") //(Loan/Deposit/Shares/Sales/Purchase/Savings/Expense/Assets/Investment)
    String appCode;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "voucher_type_id", referencedColumnName = "id")
    private VoucherType voucherType;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "vouchers_id")
    private Set<VoucherDetails> voucherDetails;
}
