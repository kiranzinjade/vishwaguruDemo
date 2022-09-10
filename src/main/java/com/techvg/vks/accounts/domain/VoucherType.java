package com.techvg.vks.accounts.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "voucher_type")
public class VoucherType extends BaseEntity<String> {

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "is_general")
    Boolean isGeneral;

    @OneToMany(mappedBy = "voucherType")
    private Set<Vouchers> vouchers;
}
