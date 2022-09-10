package com.techvg.vks.accounts.controller;

import com.techvg.vks.accounts.model.JournalDto;
import com.techvg.vks.accounts.model.JournalPageList;
import com.techvg.vks.accounts.service.JournalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/journal")
public class JournalController {
    private final JournalService journalService;

    @GetMapping
    public ResponseEntity<JournalPageList> listAllJournal(Pageable pageable) {
        return new ResponseEntity<>(journalService.listJournals(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalDto> addJournal(@Validated @RequestBody JournalDto journalDto) {
        return new ResponseEntity<>( journalService.addJournal(journalDto), HttpStatus.CREATED);
    }

    @PutMapping(path = { "/{journalId}" })
    public ResponseEntity<JournalDto> updateJournal(@PathVariable("journalId") Long journalId,
                                                         @Validated @RequestBody JournalDto journalDto, Authentication authentication) {
        return new ResponseEntity<>(journalService.updateJournal(journalId, journalDto, authentication), HttpStatus.OK);
    }

    @GetMapping({ "/{journalId}" })
    public ResponseEntity<JournalDto> getJournal(@PathVariable("journalId") Long journalId) {
        return new ResponseEntity<>(journalService.getJournalById(journalId), HttpStatus.OK);
    }

    @DeleteMapping(path = { "/{journalId}" }) // deleting data
    public ResponseEntity<JournalDto>  deleteJournalById(@PathVariable("journalId") Long journalId) {
        return new ResponseEntity<>(journalService.deleteJournalById(journalId), HttpStatus.OK);
    }

}
