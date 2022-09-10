package com.techvg.vks.accounts.controller;

import com.techvg.vks.accounts.model.DayBookDto;
import com.techvg.vks.accounts.service.DayBookService;
import com.techvg.vks.common.DateConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/daybook")
public class DayBookController {

    private final DayBookService dayBookService;

    @GetMapping(path = { "/getpostentry/{transDate}" })
    public ResponseEntity <List<DayBookDto>> listDayBookEntries(@PathVariable("transDate") String transDate) {
        return new ResponseEntity<>(
                dayBookService.listDayBookEntries(DateConverter.getDate(transDate)), HttpStatus.OK);
    }

    @PutMapping(path = { "/postentry/{transDate}" })
    public ResponseEntity<Boolean> addDayBookEntries(@PathVariable("transDate") String transDate) {
        return new ResponseEntity<>(
                dayBookService.addDayBookEntries(DateConverter.getDate(transDate)), HttpStatus.OK);
    }
}
