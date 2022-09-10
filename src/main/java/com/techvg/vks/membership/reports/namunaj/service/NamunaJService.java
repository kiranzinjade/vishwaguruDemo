package com.techvg.vks.membership.reports.namunaj.service;

import org.springframework.stereotype.Service;


@Service
public interface NamunaJService {
String NONACTIVE = "NONACTIVE";
String ACTIVE = "ACTIVE";
	byte[] getNamunaJByMemberTypes(String memberType);
	byte[] getNamunaJForActiveMember();
	byte[] getNamunaJForNonActiveMember();
}
