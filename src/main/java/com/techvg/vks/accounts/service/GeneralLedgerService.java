package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.model.GeneralLedgerDto;
import com.techvg.vks.accounts.model.GeneralLedgerPageList;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface GeneralLedgerService {
    boolean postGeneralLedgerTrans(Date transDate);
    boolean postGeneralLedger(Date transDate);
    GeneralLedgerDto updateGeneralLedgerTrans(Long generalLedgerTransId, GeneralLedgerDto generalLedgerDto, Authentication authentication);
    GeneralLedgerDto getGeneralLedgerTransById(Long generalLedgerTransId);
    GeneralLedgerDto deleteGeneralLedgerTransById(Long generalLedgerTransId);
    GeneralLedgerPageList listGeneralLedgerTrans(Pageable pageable);
}
