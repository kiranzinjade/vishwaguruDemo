package com.techvg.vks.society.reports.BorrowingLedgerReport;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;

import com.techvg.vks.society.domain.BorrowingLedgerTransaction;

@Mapper(componentModel="spring")
public interface BorrowingLedgerReportMapper {
	@IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
	
	List<BorrowingLedgerReportWrapper> mapAllBorrowingLedgerDataList(List<BorrowingLedgerTransaction> borrowingLedgerList);
	
	public BorrowingLedgerReportWrapper mapAllBorrowingLedgerData(BorrowingLedgerTransaction borrowingLedgerTransaction);
	
	@AfterMapping
	default void fillInProperties(final BorrowingLedgerTransaction borrowingLedgerTransaction, @MappingTarget final BorrowingLedgerReportWrapper wrapper ) {
		
		wrapper.setPurpose(borrowingLedgerTransaction.getBorrowingLedger().getPurpose());
		wrapper.setLoanNo(borrowingLedgerTransaction.getBorrowingLedger().getLoanNo());
		wrapper.setDate(borrowingLedgerTransaction.getBorrowingLedger().getDate());
		wrapper.setLoanAmt(borrowingLedgerTransaction.getBorrowingLedger().getLoanAmt());
		wrapper.setDueDate(borrowingLedgerTransaction.getBorrowingLedger().getDueDate());
		wrapper.setInterest(borrowingLedgerTransaction.getBorrowingLedger().getInterest());
		wrapper.setCrop(borrowingLedgerTransaction.getBorrowingLedger().getCrop());
		wrapper.setTransactionDate(borrowingLedgerTransaction.getTransactionDate());
		wrapper.setParticulars(borrowingLedgerTransaction.getParticulars());
		wrapper.setDebit(borrowingLedgerTransaction.getDebit());
		wrapper.setCredit(borrowingLedgerTransaction.getCredit());
		wrapper.setBalance(borrowingLedgerTransaction.getBalance());
		wrapper.setInitials(borrowingLedgerTransaction.getInitials());
		wrapper.setNoOfDays(borrowingLedgerTransaction.getNoOfDays());
		wrapper.setTotalInterest(borrowingLedgerTransaction.getTotalInterest());
		wrapper.setInterestDue(borrowingLedgerTransaction.getInterestDue());
		wrapper.setInterestPaid(borrowingLedgerTransaction.getInterestPaid());
		wrapper.setRemarks(borrowingLedgerTransaction.getRemarks());
		wrapper.setBankName(borrowingLedgerTransaction.borrowingLedger.getBank().getBankName());
	
	}
	

}
