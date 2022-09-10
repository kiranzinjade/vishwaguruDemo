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
import com.techvg.vks.trading.domain.PurchaseOrder;
import com.techvg.vks.trading.domain.PurchaseRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseVoucherServiceImpl implements PurchaseVoucherService {

    private final VoucherTypeService voucherTypeService;
    private final LedgerAccountsRepository ledgerAccountsRepository;
    private final VouchersRepository vouchersRepository;
    private final VoucherSeqRepository voucherSeqRepository;
    private final AccountMappingService accountMappingService;

    @Override
    public Vouchers createPurchaseVoucher(PurchaseOrder purchaseOrder) {
        String purchaseNarration = "Being purchases of Rs." + purchaseOrder.getBillAmount()  +
                " vide bill no " + purchaseOrder.getBillNo() ;

        double transAmt = purchaseOrder.getBillAmount();
        double balanceAmt = purchaseOrder.getBalanceAmount();
        boolean isSundryCreditor = false;

        if(balanceAmt !=0){
            isSundryCreditor = true;
            transAmt = purchaseOrder.getPaidAmount();
        }

        String voucherType = AccountConstants.PURCHASE_VOUCHER;
        String paymentMode = AccountConstants.CHEQUE;
        if(purchaseOrder.getPurchaseMode().equalsIgnoreCase(AccountConstants.CASH)){
            voucherType = AccountConstants.PAYMENT;
            paymentMode = AccountConstants.CASH;
        }
        VoucherType paymentVoucher = voucherTypeService.getVoucherTypeByName(voucherType);

        HashMap productMap = new HashMap<>();
        double productTotalValue=0.0;
        String productType=null;
        for (PurchaseRegister purchaseItem : purchaseOrder.getPurchaseRegisters()) {
            productType = purchaseItem.getProduct().getProductType().getType();
            double productValue = purchaseItem.getTotalAmt();
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
        vouchers.setNarration(purchaseNarration);
        vouchers.setAppCode(AccountConstants.PURCHASE_VOUCHER);

        Set<VoucherDetails> voucherDetailsSet = new HashSet<>();

        // Create credit transaction entry for cash given
        VoucherDetails creditVoucherDetails = createCreditEntry(transAmt, paymentMode, purchaseOrder );
        voucherDetailsSet.add(creditVoucherDetails);

        if(isSundryCreditor){
            VoucherDetails sundryCreditorVoucherDetails = createSundryCreditEntry(balanceAmt, purchaseOrder );
            voucherDetailsSet.add(sundryCreditorVoucherDetails);
        }

        // Create debit trans entry for each type of product
        productMap.forEach((key, value) ->{
            VoucherDetails voucherDetails = createDebitEntry((String)key, (double)value);
            voucherDetailsSet.add(voucherDetails);
        });

        vouchers.setVoucherDetails(voucherDetailsSet);
        vouchersRepository.save(vouchers);
        return vouchers;
    }

    private VoucherDetails createSundryCreditEntry(double balanceAmt, PurchaseOrder purchaseOrder) {
        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(balanceAmt);

        AccountMapping accountMapping = accountMappingService.getAccountMappingByName(MappingName.SUNDRY_CREDITOR);

        // Get Sundry Creditors A/C
        LedgerAccounts sundryCreditorAcc = accountMapping.getLedgerAccount();
        voucherDetails.setAccountHead(sundryCreditorAcc.getAccHeadCode());
        voucherDetails.setCreditAcc(sundryCreditorAcc.getAccountNo());

        return voucherDetails;
    }

    private VoucherDetails createCreditEntry(double transAmt, String paymentMode, PurchaseOrder purchaseOrder) {
        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(transAmt);

        // Get Cash on hand a/c
        if (paymentMode.equalsIgnoreCase(AccountConstants.CASH)){
            LedgerAccounts cashOnHandAcc = ledgerAccountsRepository.findByAccHeadCode(AccountConstants.CASH_ON_HAND);
            voucherDetails.setAccountHead(cashOnHandAcc.getAccHeadCode());
            voucherDetails.setCreditAcc(cashOnHandAcc.getAccountNo());
        }else{
        	LedgerAccounts chequeAcc = ledgerAccountsRepository.findByAccHeadCode(purchaseOrder.getAccHeadCode());
             voucherDetails.setCreditAcc(chequeAcc.getAccountNo());
            voucherDetails.setAccountHead(chequeAcc.getAccHeadCode());
        }
        return voucherDetails;
    }

    private VoucherDetails createDebitEntry(String key, double value){
        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(value);

        AccountMapping accountMapping = accountMappingService.getAccountMappingByNameAndType(key, MappingType.PURCHASE);
        voucherDetails.setDebitAcc(accountMapping.getLedgerAccount().getAccountNo());
        voucherDetails.setAccountHead(accountMapping.getLedgerAccHeadCode());
        return voucherDetails;
    }
}
