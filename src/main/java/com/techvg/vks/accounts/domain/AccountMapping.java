package com.techvg.vks.accounts.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_mapping")
public class AccountMapping extends BaseEntity<String> {

    @Column(name = "mapping_name")
    String mappingName;

    @Column(name = "ledger_acc_head_code")
    String ledgerAccHeadCode;

    @Column(name = "mapping_type")
    String mappingType;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch= FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="ledger_account_id")
    private LedgerAccounts ledgerAccount;

	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}

}
