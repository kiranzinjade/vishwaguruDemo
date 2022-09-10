package com.techvg.vks.accounts.service.vouchers;

import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.domain.VoucherDetails;
import com.techvg.vks.accounts.domain.VoucherType;
import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.accounts.mapper.VouchersMapper;
import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.accounts.repository.LedgerAccountsRepository;
import com.techvg.vks.accounts.repository.VoucherTypeRepository;
import com.techvg.vks.accounts.repository.VouchersRepository;
import com.techvg.vks.accounts.service.VoucherTypeService;
import com.techvg.vks.common.LoginUser;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.idgenerator.VoucherSeq;
import com.techvg.vks.idgenerator.repository.VoucherSeqRepository;
import com.techvg.vks.society.model.ExpenditureRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExpenseVoucherServiceImpl implements ExpenseVoucherService {

    private final LedgerAccountsRepository ledgerAccountsRepository;
    private final VouchersRepository vouchersRepository;
    private final VoucherSeqRepository voucherSeqRepository;
    private final VoucherTypeRepository voucherTypeRepository;


    private final VoucherTypeService voucherTypeService;
    private final VouchersMapper vouchersMapper;

    @Override
    public VouchersDto createExpenseVoucher(ExpenditureRegisterDto expenditureRegisterDto) {
        VoucherType paymentVoucher = voucherTypeService.getVoucherTypeByName(expenditureRegisterDto.getVoucherType());
        double expenseAmt = expenditureRegisterDto.getExpenditureAmt();
        String expenseNarration = "Being payment of above expense for Rs." + expenseAmt ;

        // Create Voucher No from sequence
        VoucherSeq no = new VoucherSeq();
        voucherSeqRepository.save(no);
        Long voucherNo = no.getVoucherNo();

        Vouchers vouchers = new Vouchers();
        vouchers.setVoucherDate(new Date());
        vouchers.setPreparedBy(LoginUser.getUser());
        vouchers.setVoucherType(paymentVoucher);
        vouchers.setMode(expenditureRegisterDto.getMode());
        vouchers.setVoucherNo(voucherNo);
        vouchers.setNarration(expenseNarration);
        vouchers.setAppCode(AccountConstants.EXPENSE);


        Set<VoucherDetails> voucherDetailsSet = new HashSet<>();
        // Create credit transaction entry
        if(expenditureRegisterDto.getMode().equalsIgnoreCase(AccountConstants.CASH)) {
            VoucherDetails cashVoucherDetails = createCashEntry(expenseAmt);
            voucherDetailsSet.add(cashVoucherDetails);
        }else{
            VoucherDetails chequeVoucherDetails = createChequeEntry(expenseAmt, expenditureRegisterDto);
            voucherDetailsSet.add(chequeVoucherDetails);
        }

        // Create debit trans entry for expense
        VoucherDetails expenseVoucherDetails = createExpenseEntry(expenseAmt, expenditureRegisterDto);


        voucherDetailsSet.add(expenseVoucherDetails);

        vouchers.setVoucherDetails(voucherDetailsSet);
        return vouchersMapper.toDto(vouchers);
    }

    @Override
    public VouchersDto updateExpenseVoucher(VouchersDto vouchersDto) {
        VoucherType voucherType = voucherTypeRepository.getOne(vouchersDto.getVoucherTypeId());
        Vouchers vouchers = vouchersMapper.toDomain(vouchersDto);
        vouchers.setVoucherType(voucherType);
        vouchersRepository.save(vouchers);
        return vouchersMapper.toDto(vouchers);
    }

    private VoucherDetails createCashEntry(double totalAmt){
        // Get Cash on hand a/c
        LedgerAccounts cashOnHandAcc = ledgerAccountsRepository.findByAccHeadCode(AccountConstants.CASH_ON_HAND);

        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(totalAmt);
        voucherDetails.setAccountHead(cashOnHandAcc.getAccHeadCode());
        voucherDetails.setCreditAcc(cashOnHandAcc.getAccountNo());
        return voucherDetails;
    }

    private VoucherDetails createChequeEntry(double expenseAmt, ExpenditureRegisterDto expenditureRegisterDto){
        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(expenseAmt);

        // Get related expense  a/c
        LedgerAccounts expenseAcc = ledgerAccountsRepository.findByAccHeadCode(expenditureRegisterDto.getCreditTo());
        Long expenseAccNo = expenseAcc.getAccountNo();
        voucherDetails.setCreditAcc(expenseAccNo);
        voucherDetails.setAccountHead(expenseAcc.getAccHeadCode());
        return voucherDetails;
    }

    private VoucherDetails createExpenseEntry(double expenseAmt, ExpenditureRegisterDto expenditureRegisterDto){
        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(expenseAmt);

        // Get related expense  a/c
        LedgerAccounts expenseAcc = ledgerAccountsRepository.findByAccHeadCode(expenditureRegisterDto.getDebitFrom());
        Long expenseAccNo = expenseAcc.getAccountNo();
        voucherDetails.setDebitAcc(expenseAccNo);
        voucherDetails.setAccountHead(expenseAcc.getAccHeadCode());
        return voucherDetails;
    }
}
