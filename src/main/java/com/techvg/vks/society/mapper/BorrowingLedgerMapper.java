package com.techvg.vks.society.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.society.domain.BorrowingLedger;
import com.techvg.vks.society.domain.SocietyBank;
import com.techvg.vks.society.model.BorrowingLedgerDto;
@Mapper(componentModel = "spring")
public interface BorrowingLedgerMapper {

	
	@Mapping( source = "borrowingLedger.bank.id", target = "bankId")
    @Mapping( source = "borrowingLedger.bank.bankName", target = "bankName")
	BorrowingLedgerDto borrowingLedgerToBorrowingLedgerDto(BorrowingLedger borrowingLedger);
	BorrowingLedger borrowingLedgerDtoToBorrowingLedger(BorrowingLedgerDto orrowingLedgerDto);
	
    List<BorrowingLedgerDto> domainToDtoList(List<BorrowingLedger> domainList);


}
