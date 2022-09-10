package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.Journal;
import com.techvg.vks.accounts.mapper.JournalMapper;
import com.techvg.vks.accounts.model.JournalDto;
import com.techvg.vks.accounts.model.JournalPageList;
import com.techvg.vks.accounts.repository.JournalRepository;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService {
    private final JournalMapper journalMapper;
    private final JournalRepository journalRepository;

    @Override
    public JournalDto addJournal(JournalDto journalDto) {

        Optional<Journal> journalObjOptional = journalRepository.findByName(journalDto.getName());
        if (journalObjOptional.isPresent()){
            throw new AlreadyExitsException("Journal already exists : " + journalDto.getName());
        }
        else {
            return journalMapper.toDto(journalRepository.save(journalMapper.toDomain(journalDto)));
        }
    }

    @Override
    public JournalDto updateJournal(Long journalId, JournalDto journalDto, Authentication authentication) {
        journalRepository.findById(journalId).orElseThrow(
                () -> new NotFoundException("No Journal found for Id : " +journalId));
        Journal journal = journalMapper.toDomain(journalDto);
        journal.setId(journalId);
        return journalMapper.toDto(journalRepository.save(journal));
    }

    @Override
    public JournalDto getJournalById(Long journalId) {

        Journal journal = journalRepository.findById(journalId).orElseThrow(
                () -> new NotFoundException("No Journal found for Id : " +journalId));
        return journalMapper.toDto(journal);
    }

    @Override
    public JournalDto deleteJournalById(Long journalId) {

        Journal journal = journalRepository.findById(journalId).orElseThrow(
                () -> new NotFoundException("No Journal found for Id : " +journalId));
        if (journal != null) {
            journal.setIsDeleted(true);
            journalRepository.save(journal);
        }
        return journalMapper.toDto(journal);
    }

    @Override
    public JournalPageList listJournals(Pageable pageable) {

        Page<Journal> journalPage;
        journalPage = journalRepository.findAll(pageable);

        return new JournalPageList(journalPage
                .getContent()
                .stream()
                .map(journalMapper::toDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(journalPage.getPageable().getPageNumber(),
                                journalPage.getPageable().getPageSize()),
                journalPage.getTotalElements());
    }

}
