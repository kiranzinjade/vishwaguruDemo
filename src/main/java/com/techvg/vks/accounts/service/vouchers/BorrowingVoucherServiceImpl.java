package com.techvg.vks.accounts.service.vouchers;

import com.techvg.vks.accounts.domain.*;
import com.techvg.vks.accounts.mapper.VouchersMapper;
import com.techvg.vks.accounts.repository.LedgerAccountsRepository;
import com.techvg.vks.accounts.repository.VoucherTypeRepository;
import com.techvg.vks.accounts.repository.VouchersRepository;
import com.techvg.vks.accounts.service.AccountMappingService;
import com.techvg.vks.accounts.service.VoucherTypeService;
import com.techvg.vks.common.LoginUser;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.config.MappingName;
import com.techvg.vks.idgenerator.VoucherSeq;
import com.techvg.vks.idgenerator.repository.VoucherSeqRepository;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.society.domain.LoanProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BorrowingVoucherServiceImpl implements BorrowingVoucherService {

    private final LedgerAccountsRepository ledgerAccountsRepository;
    private final VouchersRepository vouchersRepository;
    private final VoucherSeqRepository voucherSeqRepository;
    private final VoucherTypeRepository voucherTypeRepository;

    private final AccountMappingService accountMappingService;
    private final VoucherTypeService voucherTypeService;
    private final VouchersMapper vouchersMapper;

    @Override
    public Vouchers createBorrowingReceiptVoucher(LoanProduct loanProduct) {
        VoucherType receiptVoucher = voucherTypeService.getVoucherTypeByName(AccountConstants.RECEIPTS);
        String borrowingNarration = "Being the amount borrowed for Crop Loan issued of  "+loanProduct.getMaxLoanAmount() +" for " + loanProduct.getAccHeadCode();

        // Create Voucher No from sequence
        VoucherSeq no = new VoucherSeq();
        voucherSeqRepository.save(no);
        Long voucherNo = no.getVoucherNo();

        Vouchers vouchers = new Vouchers();
        vouchers.setVoucherDate(new Date());
        vouchers.setPreparedBy(LoginUser.getUser());
        vouchers.setVoucherType(receiptVoucher);
        vouchers.setMode(AccountConstants.TRANSFER);
        vouchers.setVoucherNo(voucherNo);
        vouchers.setNarration(borrowingNarration);
        vouchers.setAppCode(AccountConstants.BORROWING);
        //
        Set<VoucherDetails> voucherDetailsSet = new HashSet<>();

        // Create debit transaction in Loan Advances for the transfer entry
        VoucherDetails debitVoucherDetails = createDebitEntry(loanProduct.getParentAccHeadCode(), loanProduct.getMaxLoanAmount(),true );
        voucherDetailsSet.add(debitVoucherDetails);

        // Create credit transaction in Borrowings from DCCB for the transfer entry
        VoucherDetails creditVoucherDetails = createCreditEntry(loanProduct.getProductType(),loanProduct.getMaxLoanAmount(), false );
        voucherDetailsSet.add(creditVoucherDetails);

        vouchers.setVoucherDetails(voucherDetailsSet);
        vouchersRepository.save(vouchers);
        return vouchers;
    }

    @Override
    public Vouchers createBorrowingReceiptVoucherForMLLoan(LoanDetails loanDetails) {
        VoucherType receiptVoucher = voucherTypeService.getVoucherTypeByName(AccountConstants.RECEIPTS);
        String borrowingNarration = "Being the amount borrowed for Long-term Loan issued of Rs "+loanDetails.getLoanAmt() + " for A/C "+loanDetails.getLoanAccountNo(); //Set narration for long term loan

        if(loanDetails.getLoanProduct().getProductType().equalsIgnoreCase(LoanConstants.MID_TERM_LOAN))
            borrowingNarration = "Being the amount borrowed for Mid-term Loan issued of Rs "+loanDetails.getLoanAmt() + " for A/C "+loanDetails.getLoanAccountNo(); //Set narration for mid term loan
        // Create Voucher No from sequence
        VoucherSeq no = new VoucherSeq();
        voucherSeqRepository.save(no);
        Long voucherNo = no.getVoucherNo();

        Vouchers vouchers = new Vouchers();
        vouchers.setVoucherDate(new Date());
        vouchers.setPreparedBy(LoginUser.getUser());
        vouchers.setVoucherType(receiptVoucher);
        vouchers.setMode(AccountConstants.TRANSFER);
        vouchers.setVoucherNo(voucherNo);
        vouchers.setNarration(borrowingNarration);
        vouchers.setAppCode(AccountConstants.BORROWING);
        //
        Set<VoucherDetails> voucherDetailsSet = new HashSet<>();

        // Create debit transaction in Loan Advances for the transfer entry
        VoucherDetails debitVoucherDetails = createDebitEntry(loanDetails.getParentAccHeadCode(), loanDetails.getLoanAmt(),true );
        voucherDetailsSet.add(debitVoucherDetails);

        // Create credit transaction in Borrowings from DCCB for the transfer entry
        VoucherDetails creditVoucherDetails = createCreditEntry(loanDetails.getLoanType(), loanDetails.getLoanAmt(), false );
        voucherDetailsSet.add(creditVoucherDetails);

        vouchers.setVoucherDetails(voucherDetailsSet);
        vouchersRepository.save(vouchers);
        return vouchers;
    }

    private VoucherDetails createCreditEntry(String loanType, double amt, boolean isDebit){
        // Get Loan Advances a/c
        String mappingName = null;

        if(loanType.equals(LoanConstants.SHORT_TERM_LOAN)) {
            mappingName = MappingName.ST_LOANS_BORROWWINGS_LEDGER;
        }
        if(loanType.equals(LoanConstants.MID_TERM_LOAN)) {
            mappingName = MappingName.MTLT_LOANS_BORROWWINGS_LEDGER;
        }
        if(loanType.equals(LoanConstants.LONG_TERM_LOAN)) {
            mappingName = MappingName.MTLT_LOANS_BORROWWINGS_LEDGER;
        }
        AccountMapping accountMapping = accountMappingService.getAccountMappingByName(mappingName);
        LedgerAccounts stLoanBorrowingAcc = accountMapping.getLedgerAccount();
        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(amt);
        voucherDetails.setAccountHead(stLoanBorrowingAcc.getAccHeadCode());

        if(isDebit)
            voucherDetails.setDebitAcc(stLoanBorrowingAcc.getAccountNo());
        else
            voucherDetails.setCreditAcc(stLoanBorrowingAcc.getAccountNo());

        return voucherDetails;
    }

    private VoucherDetails createDebitEntry(String accHead, double amt, boolean isDebit){
        // Get Loan Advances a/c

        LedgerAccounts loanAdvancesAcc = ledgerAccountsRepository.findByAccHeadCode(accHead);;

        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(amt);
        voucherDetails.setAccountHead(loanAdvancesAcc.getAccHeadCode());

        if(isDebit)
            voucherDetails.setDebitAcc(loanAdvancesAcc.getAccountNo());
        else
            voucherDetails.setCreditAcc(loanAdvancesAcc.getAccountNo());

        return voucherDetails;
    }
}
