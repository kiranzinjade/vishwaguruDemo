package com.techvg.vks.society.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.society.model.ExpenditureRegisterDto;
import com.techvg.vks.society.model.ExpenditureRegisterPageList;
import com.techvg.vks.society.service.ExpenditureRegisterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/expenditure")
public class ExpenditureRegisterController {

	private final ExpenditureRegisterService expenditureRegisterService;

	@PostMapping(path = { "/expensepayment" })
	public ResponseEntity<VouchersDto> acceptExpensePayment(@RequestBody ExpenditureRegisterDto dto) {
		return new ResponseEntity<>( expenditureRegisterService.acceptExpensePayment(dto), HttpStatus.CREATED);
	}

	@PostMapping(path = { "/expensepaymentpreview" })
	public ResponseEntity<ExpenditureRegisterDto> previewExpensePayment(@RequestBody ExpenditureRegisterDto dto) {
		return new ResponseEntity<>( expenditureRegisterService.previewExpensePayment(dto), HttpStatus.OK);
	}
	
	  @PostMapping
	  public ResponseEntity<ExpenditureRegisterDto> addExpenditure(@Validated @RequestBody ExpenditureRegisterDto expenditureRegisterDto, Authentication authentication) {
	       return new ResponseEntity<>(expenditureRegisterService.addExpenditure(expenditureRegisterDto) , HttpStatus.CREATED);
	   }

	  @PutMapping(path = { "/{expenditureId}" })
	  public ResponseEntity<ExpenditureRegisterDto> updateExpenditure(@PathVariable("expenditureId") Long expenditureId,@Validated @RequestBody ExpenditureRegisterDto expenditureRegisterDto) {
	       return new ResponseEntity<>(expenditureRegisterService.updateExpenditure(expenditureId,expenditureRegisterDto) , HttpStatus.OK);
	   }
	  
	  @GetMapping(path = { "/{expenditureId}" }) 
	    public ResponseEntity<ExpenditureRegisterDto> getExpenditure(@PathVariable("expenditureId") Long expenditureId) {
	        return new ResponseEntity<>(expenditureRegisterService.getExpenditure(expenditureId), HttpStatus.OK);
	    }
	 
	  
	  @DeleteMapping(path = { "/{expenditureId}" }) 
	    public ResponseEntity<ExpenditureRegisterDto> deleteExpenditureById(@PathVariable("expenditureId") Long expenditureId) {
	        return new ResponseEntity<>(expenditureRegisterService.deleteExpenditureById(expenditureId), HttpStatus.OK);
	    }
	  
	  @GetMapping
		public ResponseEntity<ExpenditureRegisterPageList> listAllExpenditures(Pageable pageable) {

			log.debug("REST request to get a all records");
			ExpenditureRegisterPageList expenditureRegisterPageList = expenditureRegisterService.listExpenditures(pageable);
			return new ResponseEntity<>(expenditureRegisterPageList, HttpStatus.OK);
		}
	  
	  @GetMapping(path={ "/byDate/tradelist/{fromdate}/{todate}" })
		public List<ExpenditureRegisterDto> getTradeExpenseList(@PathVariable("fromdate") String fromdate,@PathVariable("todate") String  todate) {
			log.debug("REST request to get Account No : {}");
		return expenditureRegisterService.getTradeExpenseList(fromdate, todate);
		}  
	  
	  @GetMapping(path={ "/byDate/societylist/{fromdate}/{todate}" })
		public List<ExpenditureRegisterDto> getSocietyExpenseList(@PathVariable("fromdate") String fromdate,@PathVariable("todate") String  todate) {
			log.debug("REST request to get Account No : {}");
		return expenditureRegisterService.getSocietyExpenseList(fromdate, todate);
		}  
	  
	  @GetMapping(path={ "/byDate/provisionlist/{fromdate}/{todate}" })
		public List<ExpenditureRegisterDto> getProvisionExpenseList(@PathVariable("fromdate") String fromdate,@PathVariable("todate") String  todate) {
			log.debug("REST request to get Account No : {}");
		return expenditureRegisterService.getProvisionExpenseList(fromdate, todate);
		}  
	  
	  @GetMapping(path = { "/listByTrade" })
	  public ResponseEntity<ExpenditureRegisterPageList> getListByTradeExpense(Pageable pageable) {
		  ExpenditureRegisterPageList expenditureRegisterPageList = expenditureRegisterService.getListByTradeExpense(pageable);
		  return new ResponseEntity<>(expenditureRegisterPageList,HttpStatus.OK);
	  }
	  @GetMapping(path = { "/listBySociety" })
	  public ResponseEntity<ExpenditureRegisterPageList> getListBySocietyExpense(Pageable pageable) {
		  ExpenditureRegisterPageList expenditureRegisterPageList = expenditureRegisterService.getListBySocietyExpense(pageable);
		  return new ResponseEntity<>(expenditureRegisterPageList,HttpStatus.OK);
	  }
	  @GetMapping(path = { "/listByProvision" })
	  public ResponseEntity<ExpenditureRegisterPageList> getListByProvisionExpense(Pageable pageable) {
		  ExpenditureRegisterPageList expenditureRegisterPageList = expenditureRegisterService.getListByProvisionExpense(pageable);
		  return new ResponseEntity<>(expenditureRegisterPageList,HttpStatus.OK);
	  }
	  
}
