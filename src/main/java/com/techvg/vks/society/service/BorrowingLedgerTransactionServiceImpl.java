package com.techvg.vks.society.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techvg.vks.common.DateConverter;
import com.techvg.vks.config.DepositConstants;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.BorrowingLedger;
import com.techvg.vks.society.domain.BorrowingLedgerTransaction;
import com.techvg.vks.society.mapper.BorrowingLedgerTransactionMapper;
import com.techvg.vks.society.model.BorrowingLedgerTransactionDto;
import com.techvg.vks.society.repository.BorrowingLedgerRepository;
import com.techvg.vks.society.repository.BorrowingLedgerTransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowingLedgerTransactionServiceImpl implements BorrowingLedgerTransactionService {

	private final BorrowingLedgerTransactionMapper borrowingLedgerTransactionMapper;
	private final BorrowingLedgerTransactionRepository borrowingLedgerTransactionRepository;
	private final BorrowingLedgerRepository borrowingLedgerRepository;

	
	  @Override
	  public BorrowingLedgerTransactionDto addTransactionDetails(BorrowingLedgerTransactionDto borrowingLedgerTransactionDto, String transactionCriteria) {

			
			BorrowingLedgerTransaction borrowingTransaction = borrowingLedgerTransactionMapper.borrowingLedgerTransactionDtoToBorrowingLedgerTransaction(borrowingLedgerTransactionDto);
			BorrowingLedger borrowingLedger = borrowingLedgerRepository.findById(borrowingLedgerTransactionDto.getBorrowingId()).orElseThrow(() -> new NotFoundException(
							"No borrowlist found for id : " + borrowingLedgerTransactionDto.getBorrowingId()));
			borrowingTransaction.setBorrowingLedger(borrowingLedger);

		  BorrowingLedgerTransaction borrowingTransaction1 = borrowingLedgerTransactionRepository.findByLastRecord(borrowingLedgerTransactionDto.getBorrowingId());
			if (borrowingTransaction1 == null)
			{
					if(transactionCriteria.equals(DepositConstants.DEPOSIT_CREDIT))
					{
						borrowingTransaction.setBalance(borrowingLedgerTransactionDto.getCredit());
		  
					} else
					{
						throw new AlreadyExitsException(" No Balance Exist: " + borrowingTransaction.getBalance());
					}
			}
	  
	  else 
	  {
		  if (transactionCriteria.equals(DepositConstants.DEPOSIT_DEBIT))
		  {
			  Date startDate=borrowingTransaction1.getTransactionDate();
			  Date endDate=borrowingLedgerTransactionDto.getTransactionDate(); 
			  Integer dayDiff=DateConverter.dayDiff(startDate, endDate);
			  borrowingTransaction.setNoOfDays(dayDiff);
			  
			  Double balance = borrowingTransaction1.getBalance(); 
			  Double roi =borrowingLedger.getInterest(); 
			  Integer duration =borrowingTransaction.getNoOfDays();
			  Double perdayinterest = (balance * roi /100 / 365);
			  Double totalInterest = perdayinterest * duration;
			  borrowingTransaction.setTotalInterest(totalInterest);
			  
			 if((borrowingLedgerTransactionDto.getDebit()) < (borrowingTransaction.getTotalInterest()))
			  {
				  borrowingTransaction.setBalance(borrowingTransaction1.getBalance());
				  borrowingTransaction.setInterestPaid(borrowingTransaction.getDebit());
				  borrowingTransaction.setInterestDue(borrowingTransaction.getTotalInterest() - borrowingTransaction.getInterestPaid());
			  }
			  
			  else  {

	  		  borrowingTransaction.setBalance((borrowingTransaction1.getBalance()) - (borrowingLedgerTransactionDto.getDebit()-borrowingTransaction.getTotalInterest()));
			
			  
			  borrowingTransaction.setInterestPaid(borrowingTransaction.getTotalInterest());
			  
			  borrowingTransaction.setInterestDue(borrowingTransaction.getTotalInterest() - borrowingTransaction.getInterestPaid());
			  }
		  
		  }
		  
		  else
		  {
			  borrowingTransaction.setBalance((borrowingTransaction1.getBalance()) + (borrowingLedgerTransactionDto.getCredit()));
	
			  Date startDate=borrowingTransaction1.getTransactionDate(); 
			  Date endDate=borrowingLedgerTransactionDto.getTransactionDate(); 
			  Integer dayDiff=DateConverter.dayDiff(startDate, endDate);
			  borrowingTransaction.setNoOfDays(dayDiff);
			  
			  Double balance = borrowingTransaction.getBalance(); 
			  Double roi =borrowingLedger.getInterest(); 
			  Integer duration =borrowingTransaction.getNoOfDays();
			  Double perdayinterest = (balance * roi /100 / 365);
			  Double totalInterest = perdayinterest * duration;
			  borrowingTransaction.setTotalInterest(totalInterest);
			  
			  borrowingTransaction.setInterestDue(borrowingTransaction.getTotalInterest() - borrowingLedgerTransactionDto.getInterestPaid());
		  }

				}
			return borrowingLedgerTransactionMapper.borrowingLedgerTransactionToBorrowingLedgerTransactionDto(borrowingLedgerTransactionRepository.save(borrowingTransaction));
			
	  }


	@Override
	public double getTotalLoanAmount() {
		double totalLoanAmount=borrowingLedgerTransactionRepository.findTotalLoanAmount();
		return totalLoanAmount;
	}


	@Override
	public double getBalanceLoanAmount() {
		List<Long> borrowingList=borrowingLedgerTransactionRepository.findRemainingAmount();

		double sum=0;
		for(int j=0;j<borrowingList.size();j++) {
			Optional<BorrowingLedger> borrow=borrowingLedgerRepository.findById(borrowingList.get(j));

			BorrowingLedgerTransaction borrowingLedgerTransaction=borrowingLedgerTransactionRepository.findByLastRecord(borrow.get().getId());
			sum=sum+borrowingLedgerTransaction.getBalance();
		}
		return sum;
	}
}
		