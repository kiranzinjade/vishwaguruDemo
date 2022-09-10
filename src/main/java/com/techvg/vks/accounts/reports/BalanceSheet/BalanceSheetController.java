package com.techvg.vks.accounts.reports.BalanceSheet;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.model.LedgerAccountsDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/balancesheet")
public class BalanceSheetController {

	private final BalanceSheetService balanceSheetService;
	
	@GetMapping
	public byte[] generateBalanceSheet()  {
		log.debug("REST request to get Transaction Date : {}");
	return balanceSheetService.generateBalanceSheet();
	}
	
	@GetMapping("/list")
	public List<LedgerAccountsDto> listLiabilities(){
		return balanceSheetService.listLiabilities();
	}
	
	@GetMapping("/assetsList")
	public List<LedgerAccountsDto> listAssets(){
		return balanceSheetService.listAssets();
	}
	
}
