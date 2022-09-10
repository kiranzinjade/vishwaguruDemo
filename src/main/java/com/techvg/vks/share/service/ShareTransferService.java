package com.techvg.vks.share.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ShareTransferService {

	Boolean isMemberHasNominee(Long memberid,Long nomineeid);
	Boolean isNomineeMember(Long nomineeid);
	ResponseEntity<?> shareTransfer(Long memberid,Long nomineeid);
	
}
