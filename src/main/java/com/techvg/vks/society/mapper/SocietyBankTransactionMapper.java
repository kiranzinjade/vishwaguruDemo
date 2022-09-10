package com.techvg.vks.society.mapper;

import com.techvg.vks.accounts.domain.CashBook;
import com.techvg.vks.accounts.domain.VoucherTrans;
import com.techvg.vks.config.AccountConstants;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;

import com.techvg.vks.society.domain.SocietyBank;
import com.techvg.vks.society.domain.SocietyBankTransaction;
import com.techvg.vks.society.model.SocietyBankTransactionDto;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SocietyBankTransactionMapper {
	
	@Mapping(source="societyBank.id",target="bankId")
	SocietyBankTransactionDto toDto(SocietyBankTransaction domain);
	SocietyBankTransaction toDomain(SocietyBankTransactionDto dto);
	
	List<SocietyBankTransactionDto> domainToDtoList(List<SocietyBankTransaction> domainList);
	

	@Mapping(target = "debit", ignore = true )
	@Mapping(target = "credit", ignore = true )
	@Mapping(source = "remark", target = "particulars")
	@Mapping(source = "transDate", target = "transactionDate")
	@Mapping(source = "voucherNo", target = "voucherNo")
	SocietyBankTransaction voucherTransToBankbook(VoucherTrans domain);

	@AfterMapping
	default void updateBankBook(final VoucherTrans domain,
								@MappingTarget final SocietyBankTransaction bankTransaction){
		if(domain.getDebitAmt()!=null) {
			bankTransaction.setTransType(AccountConstants.CREDIT);
			bankTransaction.setCredit(domain.getDebitAmt());
		}
		else if(domain.getCreditAmt()!=null) {
			bankTransaction.setTransType(AccountConstants.DEBIT);
			bankTransaction.setDebit(domain.getCreditAmt());
		}
	}
	
	
}
