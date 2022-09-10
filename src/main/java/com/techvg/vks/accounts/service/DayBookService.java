package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.DayBook;
import com.techvg.vks.accounts.model.DayBookDto;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface DayBookService {

    DayBookDto getDayBookEntryById(Long dayBookId);
    DayBookDto deleteDayBookEntryById(Long dayBookId);
    List<DayBookDto> listDayBookEntries(Date transDate);
    boolean addDayBookEntries(Date transDate);
    DayBook addDayBook(DayBook dayBook);
    Optional<DayBook> findDayBook(DayBook dayBook);
    double updateBalance(double balance, double amt, String accountType, String transType);
}
