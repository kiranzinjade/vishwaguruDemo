package com.techvg.vks.accounts.service.vouchers;

import com.techvg.vks.accounts.domain.*;
import com.techvg.vks.accounts.repository.LedgerAccountsRepository;
import com.techvg.vks.accounts.repository.VouchersRepository;
import com.techvg.vks.accounts.service.AccountMappingService;
import com.techvg.vks.accounts.service.VoucherTypeService;
import com.techvg.vks.common.LoginUser;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.config.MappingName;
import com.techvg.vks.config.MappingType;
import com.techvg.vks.idgenerator.VoucherSeq;
import com.techvg.vks.idgenerator.repository.VoucherSeqRepository;
import com.techvg.vks.membership.domain.MemberBank;
import com.techvg.vks.trading.domain.SalesOrder;
import com.techvg.vks.trading.domain.SalesRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SalesVoucherServiceImpl implements SalesVoucherService {

    private final VoucherTypeService voucherTypeService;
    private final LedgerAccountsRepository ledgerAccountsRepository;
    private final VouchersRepository vouchersRepository;
    private final VoucherSeqRepository voucherSeqRepository;
    private final AccountMappingService accountMappingService;

    @Override
    public Vouchers createSalesVoucher(SalesOrder salesOrder) {
        String salesNarration = "Being sales of Rs." + salesOrder.getBillAmount()  +
                " vide bill no " + salesOrder.getBillNo() ;

        double transAmt = salesOrder.getBillAmount();
        double balanceAmt = salesOrder.getBalanceAmount();
        boolean isSundryDebitor = false;

        if(balanceAmt !=0){
            isSundryDebitor = true;
            transAmt = salesOrder.getPaidAmount();
        }

        String voucherType = AccountConstants.SALES_VOUCHER;
        String paymentMode = AccountConstants.CHEQUE;
        if(salesOrder.getSalesMode().equalsIgnoreCase(AccountConstants.CASH)){
            voucherType = AccountConstants.PAYMENT;
            paymentMode = AccountConstants.CASH;
        }
        VoucherType paymentVoucher = voucherTypeService.getVoucherTypeByName(voucherType);

        HashMap productMap = new HashMap<>();
        double productTotalValue=0.0;
        String productType=null;
        for (SalesRegister salesItem : salesOrder.getSalesRegisters()) {
            productType = salesItem.getProduct().getProductType().getType();
            double productValue = salesItem.getTotalAmt();
            if(productMap.containsKey(productType)){
                productTotalValue = (double)productMap.get(productType);
                productTotalValue +=productValue;
                productMap.put(productType,productTotalValue);
            }
            else{
                productMap.put(productType,productValue);
            }
        }

        // Create Voucher No from sequence
        VoucherSeq no = new VoucherSeq();
        voucherSeqRepository.save(no);
        Long voucherNo = no.getVoucherNo();

        Vouchers vouchers = new Vouchers();
        vouchers.setVoucherDate(new Date());
        vouchers.setPreparedBy(LoginUser.getUser());
        vouchers.setVoucherType(paymentVoucher);
        vouchers.setMode(paymentMode);
        vouchers.setVoucherNo(voucherNo);
        vouchers.setNarration(salesNarration);
        vouchers.setAppCode(AccountConstants.SALES_VOUCHER);

        Set<VoucherDetails> voucherDetailsSet = new HashSet<>();

        // Create debit transaction entry for cash given
        VoucherDetails creditVoucherDetails = createDebitEntry(transAmt, paymentMode, salesOrder );
        voucherDetailsSet.add(creditVoucherDetails);

        if(isSundryDebitor){
            VoucherDetails sundryCreditorVoucherDetails = createSundryDebitEntry(balanceAmt, salesOrder );
            voucherDetailsSet.add(sundryCreditorVoucherDetails);
        }

        // Create credit trans entry for each type of product
        productMap.forEach((key, value) ->{
            VoucherDetails voucherDetails = createCreditEntry((String)key, (double)value);
            voucherDetailsSet.add(voucherDetails);
        });

        vouchers.setVoucherDetails(voucherDetailsSet);
        vouchersRepository.save(vouchers);
        return vouchers;
    }

    private VoucherDetails createSundryDebitEntry(double balanceAmt, SalesOrder salesOrder) {
        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(balanceAmt);

        AccountMapping accountMapping = accountMappingService.getAccountMappingByName(MappingName.SUNDRY_DEBTOR);

        // Get Sundry Debtor A/C
        LedgerAccounts sundryDebtorAcc = accountMapping.getLedgerAccount();
        voucherDetails.setAccountHead(sundryDebtorAcc.getAccHeadCode());
        voucherDetails.setDebitAcc(sundryDebtorAcc.getAccountNo());

        return voucherDetails;
    }

    private VoucherDetails createDebitEntry(double transAmt, String paymentMode, SalesOrder salesOrder) {
        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(transAmt);

        // Get Cash on hand a/c
        if (paymentMode.equalsIgnoreCase(AccountConstants.CASH)){
            LedgerAccounts cashOnHandAcc = ledgerAccountsRepository.findByAccHeadCode(AccountConstants.CASH_ON_HAND);
            voucherDetails.setAccountHead(cashOnHandAcc.getAccHeadCode());
            voucherDetails.setDebitAcc(cashOnHandAcc.getAccountNo());
        }else{
            Long memberAcc = 0L;
            for (MemberBank bank : salesOrder.getMember().getMemberBank()) {
                memberAcc = bank.getAccountNumber();
            }
            voucherDetails.setDebitAcc(memberAcc);
            voucherDetails.setAccountHead(salesOrder.getMember().getFirstName() + " " + salesOrder.getMember().getLastName());
        }
        return voucherDetails;
    }

    private VoucherDetails createCreditEntry(String key, double value){
        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(value);

        AccountMapping accountMapping = accountMappingService.getAccountMappingByNameAndType(key, MappingType.SALES);
        voucherDetails.setCreditAcc(accountMapping.getLedgerAccount().getAccountNo());
        voucherDetails.setAccountHead(accountMapping.getLedgerAccHeadCode());
        return voucherDetails;
    }
}
