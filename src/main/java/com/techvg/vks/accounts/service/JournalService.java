package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.model.JournalDto;
import com.techvg.vks.accounts.model.JournalPageList;
import com.techvg.vks.accounts.model.VoucherTypeDto;
import com.techvg.vks.accounts.model.VoucherTypePageList;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface JournalService {
    JournalDto addJournal(JournalDto journalDto);
    JournalDto updateJournal(Long journalId, JournalDto journalDto, Authentication authentication);
    JournalDto getJournalById(Long journalId);
    JournalDto deleteJournalById(Long journalId);
    JournalPageList listJournals(Pageable pageable);
}
