package com.techvg.vks.loan.reports.defaulterlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.loan.model.LoanDemandDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/defaulterlist")
public class DefaulterListController {

	@Autowired
	DefaulterListService defaulterListService;

	@GetMapping({ "/{loanType}" })
	public byte[] getLoanDemandDetails(@PathVariable("loanType") String loanType) {
		return defaulterListService.getDefaulterListReport(loanType);
	}

}
