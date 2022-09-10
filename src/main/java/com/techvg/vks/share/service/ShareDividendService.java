package com.techvg.vks.share.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ShareDividendService {

	
public Double getShareCapital(int year);
public ResponseEntity<?> calculateShareDividend();
public ResponseEntity<?> approveShareDividend();
public void saveShareDividend(double dividend,int year);
public void saveShareSinglePrice(double price,int year);
Double getTotalShareDividend();
Double getPerSharePrice();
Integer getTotalNoOfShares();
}
