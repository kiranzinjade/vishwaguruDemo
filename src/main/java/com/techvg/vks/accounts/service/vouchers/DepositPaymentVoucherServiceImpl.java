package com.techvg.vks.accounts.service.vouchers;

import com.techvg.vks.accounts.domain.*;
import com.techvg.vks.accounts.repository.LedgerAccountsRepository;
import com.techvg.vks.accounts.repository.VouchersRepository;
import com.techvg.vks.accounts.service.AccountMappingService;
import com.techvg.vks.accounts.service.VoucherTypeService;
import com.techvg.vks.common.LoginUser;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.config.MappingName;
import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.idgenerator.VoucherSeq;
import com.techvg.vks.idgenerator.repository.VoucherSeqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DepositPaymentVoucherServiceImpl implements DepositPaymentVoucherService {
    private final LedgerAccountsRepository ledgerAccountsRepository;
    private final VouchersRepository vouchersRepository;
    private final VoucherSeqRepository voucherSeqRepository;
    private final AccountMappingService accountMappingService;
    private final VoucherTypeService voucherTypeService;

    @Override
    public Vouchers depositPaymentVoucher(Deposit deposit) {
        VoucherType paymentVoucher = voucherTypeService.getVoucherTypeByName(AccountConstants.PAYMENT);
        String particulars = "Being payment of deposit A/C no \n" + deposit.getAccountNo() + " of " +
                deposit.getMember().getFirstName() + " " + deposit.getMember().getLastName() + "\n";
        // Create Voucher No from sequence
        VoucherSeq no = new VoucherSeq();
        voucherSeqRepository.save(no);
        Long voucherNo = no.getVoucherNo();

        Vouchers vouchers = new Vouchers();
        vouchers.setVoucherDate(new Date());
        vouchers.setPreparedBy(LoginUser.getUser());
        vouchers.setVoucherType(paymentVoucher);
        vouchers.setMode(AccountConstants.CASH);
        vouchers.setVoucherNo(voucherNo);
        vouchers.setNarration(particulars);
        vouchers.setAppCode(AccountConstants.DEPOSIT);

        Set<VoucherDetails> voucherDetailsSet = new HashSet<>();

        // Create debit transaction in FD Deposits
        VoucherDetails debitVoucherDetails = createDebitEntry(deposit, deposit.getDepositAmount(),true );
        voucherDetailsSet.add(debitVoucherDetails);

        // Create debit transaction for interest
        if (deposit.getInterestEarned() > 0) {
            VoucherDetails debitInterestVoucherDetails = createInterestDebitEntry(deposit, true );
            voucherDetailsSet.add(debitInterestVoucherDetails);
        }

        // Create credit cash transaction for interest + deposit (Maturity amt)
        VoucherDetails cashVoucherDetails = createCashEntry(deposit.getMaturityAmount(), false );

        voucherDetailsSet.add(cashVoucherDetails);

        vouchers.setVoucherDetails(voucherDetailsSet);
        vouchersRepository.save(vouchers);
        return vouchers;
    }

    private VoucherDetails createCashEntry(double totalAmt, boolean isDebit){
        // Get Cash on hand a/c
        LedgerAccounts cashOnHandAcc = ledgerAccountsRepository.findByAccHeadCode(AccountConstants.CASH_ON_HAND);

        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(totalAmt);
        voucherDetails.setAccountHead(cashOnHandAcc.getAccHeadCode());

        if(isDebit)
            voucherDetails.setDebitAcc(cashOnHandAcc.getAccountNo());
        else
            voucherDetails.setCreditAcc(cashOnHandAcc.getAccountNo());

        return voucherDetails;
    }

    private VoucherDetails createDebitEntry(Deposit deposit, double amt, boolean isDebit){
        // Get Loan Advances a/c

        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(amt);
        voucherDetails.setAccountHead(deposit.getAccHead());

        if(isDebit)
            voucherDetails.setDebitAcc(deposit.getAccountNo());
        else
            voucherDetails.setCreditAcc(deposit.getAccountNo());

        return voucherDetails;
    }

    private VoucherDetails createInterestDebitEntry(Deposit deposit, boolean isDebit){

        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(deposit.getInterestEarned());

        String mappingName = MappingName.DEPOSIT_INTEREST_RD;
        if(deposit.getRecurringAmount()==0.0){
            mappingName = MappingName.DEPOSIT_INTEREST_FD;
        }

        AccountMapping accountMapping = accountMappingService.getAccountMappingByName(mappingName);

        voucherDetails.setAccountHead(accountMapping.getLedgerAccHeadCode());

        if(isDebit)
            voucherDetails.setDebitAcc(accountMapping.getLedgerAccount().getAccountNo());
        else
            voucherDetails.setCreditAcc(accountMapping.getLedgerAccount().getAccountNo());

        return voucherDetails;
    }

}