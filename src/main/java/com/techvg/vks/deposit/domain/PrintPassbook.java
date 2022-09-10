package com.techvg.vks.deposit.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.techvg.vks.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "print_passbook")
public class PrintPassbook extends BaseEntity<String> {

	@Column(name = "account_no")
	Long accountNo;

	@Column(name = "printing_date")
	Date printingDate;

	@Column(name="printing_status")
	String printingStatus; //InProgress or Complete

	@Column(name = "deposit_ledger_id")
	public Integer depositLedgerMaxId;

}
