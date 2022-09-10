package com.techvg.vks.deposit.service;

import com.techvg.vks.config.DepositConstants;
import com.techvg.vks.deposit.domain.DepositLedger;
import com.techvg.vks.deposit.mapper.DepositLedgerMapper;
import com.techvg.vks.deposit.mapper.PrintPassbookMapper;
import com.techvg.vks.deposit.model.PrintPassbookDto;
import com.techvg.vks.deposit.repository.DepositLedgerRepository;
import com.techvg.vks.deposit.repository.PrintPassbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrintPassbookServiceImpl implements PrintPassbookService {
    private final DepositLedgerMapper depositLedgerMapper;
    private final DepositLedgerRepository depositLedgerRepository;
    private final PrintPassbookRepository printPassbookRepository;
    private final PrintPassbookMapper printPassbookMapper;

    @Override
    public List<DepositLedger> listPassbookEntries(Long accountNo) {
        List<DepositLedger> transList = null;
        Optional<Long> maxIdOpt = printPassbookRepository.findMaxDepositLedgerIdByAccountNo(accountNo);
        if(maxIdOpt.isPresent()){
            Long maxId = maxIdOpt.get();
            transList = depositLedgerRepository.findByAccountNoAndId(accountNo, maxId);
        }else {
            transList = depositLedgerRepository.findByAccountNo(accountNo);
        }

        if(transList !=null && transList.size() > 0) {
            Optional<Integer> depositMaxId = depositLedgerRepository.findMaxIdByAccountNo(accountNo);
            if (depositMaxId.isPresent()) {
                PrintPassbookDto printPassbookDto = PrintPassbookDto.builder()
                        .accountNo(accountNo)
                        .printingDate(new Date())
                        .depositLedgerMaxId(depositMaxId.get())
                        .printingStatus(DepositConstants.PASSBOOK_PRINT_INPROGRESS)
                        .build();
                printPassbookRepository.save(printPassbookMapper.printPassbookDtoToPrintPassbook(printPassbookDto));
            }
        }
     return transList;
    }

    @Override
    public List<DepositLedger> listPassbookEntries(Long accountNo, Long transactionId) {
        List<DepositLedger> transList = depositLedgerRepository.findByAccountNoAndId(accountNo, transactionId);

        if(!transList.isEmpty() && transList.size() > 0) {
            Optional<Integer> depositMaxId = depositLedgerRepository.findMaxIdByAccountNo(accountNo);
            if (depositMaxId.isPresent()) {
                PrintPassbookDto printPassbookDto = PrintPassbookDto.builder()
                        .accountNo(accountNo)
                        .printingDate(new Date())
                        .depositLedgerMaxId(depositMaxId.get())
                        .printingStatus(DepositConstants.PASSBOOK_PRINT_INPROGRESS)
                        .build();
                printPassbookRepository.save(printPassbookMapper.printPassbookDtoToPrintPassbook(printPassbookDto));
            }
        }
        return transList;
    }
}
