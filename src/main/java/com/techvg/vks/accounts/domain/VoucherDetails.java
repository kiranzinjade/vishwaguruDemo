package com.techvg.vks.accounts.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "voucher_details")
public class VoucherDetails extends BaseEntity<String> {

    @Column(name = "debit_acc")
    Long debitAcc;

    @Column(name = "credit_acc")
    Long creditAcc;

    @Column(name = "acc_head")
    String accountHead;

    @Column(name = "trans_amount")
    Double transAmount;
    
    

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "vouchers_id")
    private Vouchers vouchers;
}
