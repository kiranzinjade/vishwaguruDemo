package com.techvg.vks.accounts.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_type")
public class AccountType extends BaseEntity<String> {

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    //@JsonBackReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "accountType", fetch= FetchType.LAZY)
    private Set<LedgerAccounts> ledgerAccounts;
}