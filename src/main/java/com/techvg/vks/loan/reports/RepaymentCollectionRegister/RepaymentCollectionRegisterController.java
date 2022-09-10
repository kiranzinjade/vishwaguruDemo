package com.techvg.vks.loan.reports.RepaymentCollectionRegister;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/repaymentregister")
public class RepaymentCollectionRegisterController {

	@Autowired
	RepaymentCollectionRegisterService  repaymentCollectionRegisterService;
	
	@GetMapping({ "/{loanType}/{loanClassfication}" })
	public byte[] getLoanDetails(@PathVariable("loanType") String loanType,@PathVariable("loanClassfication") String loanClassfication) {
		return repaymentCollectionRegisterService.getRepaymentCollectionRegisterReport(loanType,loanClassfication);
	}
			 
}
