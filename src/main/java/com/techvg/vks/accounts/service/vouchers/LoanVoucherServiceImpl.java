package com.techvg.vks.accounts.service.vouchers;

import com.techvg.vks.accounts.domain.*;
import com.techvg.vks.accounts.mapper.VouchersMapper;
import com.techvg.vks.accounts.model.VouchersDto;
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
import com.techvg.vks.loan.domain.Disbursement;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.model.RepaymentDto;
import com.techvg.vks.share.domain.Shares;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoanVoucherServiceImpl implements LoanVoucherService {
    private final VoucherTypeService voucherTypeService;
    private final LedgerAccountsRepository ledgerAccountsRepository;
    private final VouchersRepository vouchersRepository;
    private final VoucherSeqRepository voucherSeqRepository;
    private final ShareRegistrationVouchersService shareRegistrationVouchersService;
    private final AccountMappingService accountMappingService;
    private final VouchersMapper vouchersMapper;
    private final VoucherTypeRepository voucherTypeRepository;

    @Override
    public Vouchers createLoanPaymentVoucher(LoanDetails loanDetails) {
      return createLoanPaymentReceipt(loanDetails, loanDetails.getDisbursementAmt());
    }
    
    @Override
    public Vouchers createSharePaymentVoucher(LoanDetails loanDetails, Shares shares) {
        if(shares !=null) {
        	return shareRegistrationVouchersService.createShareAllocationReceiptVoucher(loanDetails, shares);
        }
      return null;
    }

    private Vouchers createLoanPaymentReceipt(LoanDetails loanDetails, Double disbursementAmt)
    {
        VoucherType paymentVoucher = voucherTypeService.getVoucherTypeByName(AccountConstants.PAYMENT);
        double loanAmt = disbursementAmt;
        String loanType=loanDetails.getLoanType();
        String type = null;
        if(loanType.equals((LoanConstants.SHORT_TERM_LOAN))){
        	 type="ST";
        }
        if(loanType.equals(LoanConstants.MID_TERM_LOAN)) {
        	type="MT";
        }
        if(loanType.equals(LoanConstants.LONG_TERM_LOAN)) {
        	type="LT";
        }
        String loanNarration = "Being disbursement of Rs." + loanAmt +
                " as "+type+" Loan no " + loanDetails.getLoanAccountNo();


        // Create Voucher No from sequence
        VoucherSeq no = new VoucherSeq();
        voucherSeqRepository.save(no);
        Long voucherNo = no.getVoucherNo();

        Vouchers vouchers = new Vouchers();
        vouchers.setVoucherDate(new Date());
        vouchers.setPreparedBy(LoginUser.getUser());
        vouchers.setVoucherType(paymentVoucher);
        vouchers.setMode(AccountConstants.TRANSFER);
        vouchers.setVoucherNo(voucherNo);
        vouchers.setNarration(loanNarration);
        vouchers.setAppCode(AccountConstants.LOAN);

        Set<VoucherDetails> voucherDetailsSet = new HashSet<>();

        // Create credit transaction entry for cash given
        VoucherDetails cashVoucherDetails = createCashEntry(loanAmt, false );
        // Create debit trans entry for loan account
        VoucherDetails loanVoucherDetails = createLoanEntry(loanAmt, loanDetails);

        voucherDetailsSet.add(cashVoucherDetails);
        voucherDetailsSet.add(loanVoucherDetails);

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

    private VoucherDetails createLoanEntry(double loanAmt, LoanDetails loanDetails){
        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(loanAmt);

        // Get Individual Loan a/c
        Long memberLoanAccNo = loanDetails.getLoanAccountNo();
        voucherDetails.setDebitAcc(memberLoanAccNo);
        voucherDetails.setAccountHead(loanDetails.getLoanAccName());
        return voucherDetails;
    }

    private VoucherDetails createLoanPrincipalEntry(LoanDetails loanDetails, double amount, boolean isDebit){
        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(amount);
        voucherDetails.setAccountHead(loanDetails.getLoanAccName());

        // Get Individual Loan a/c
        Long memberLoanAccNo = loanDetails.getLoanAccountNo();
        if(isDebit)
            voucherDetails.setDebitAcc(memberLoanAccNo);
        else
            voucherDetails.setCreditAcc(memberLoanAccNo);

        return voucherDetails;
    }

    private VoucherDetails createLoanInterestEntry(double amount, boolean isOverDue, String loanType){
        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(amount);
        String mappingName = null;
       
        if(isOverDue){
        	 if(loanType.equals(LoanConstants.SHORT_TERM_LOAN)) {
        		  mappingName = MappingName.ST_LOANS_OVERDUE_INTEREST;
             }
        	 if(loanType.equals(LoanConstants.MID_TERM_LOAN)) {
        		 mappingName = MappingName.MT_LOANS_OVERDUE_INTEREST;
        	 }
        	 if(loanType.equals(LoanConstants.LONG_TERM_LOAN)) {
        		 mappingName = MappingName.LT_LOANS_OVERDUE_INTEREST;
        	 }
            //Get Overdue Interest account for Short term loan
            AccountMapping accountMapping = accountMappingService.getAccountMappingByName(mappingName);
            voucherDetails.setAccountHead(accountMapping.getLedgerAccHeadCode());
            voucherDetails.setCreditAcc(accountMapping.getLedgerAccount().getAccountNo());
        }
        else{
        	 if(loanType.equals(LoanConstants.SHORT_TERM_LOAN)) {
       		  mappingName = MappingName.ST_LOANS_NON_OVERDUE_INTEREST;
            }
       	 if(loanType.equals(LoanConstants.MID_TERM_LOAN)) {
       		 mappingName = MappingName.MT_LOANS_NON_OVERDUE_INTEREST;
       	 }
       	 if(loanType.equals(LoanConstants.LONG_TERM_LOAN)) {
       		 mappingName = MappingName.LT_LOANS_NON_OVERDUE_INTEREST;
       	 }
            // Get Regular Interest account for short term loan
            AccountMapping accountMapping = accountMappingService.getAccountMappingByName(mappingName);
            voucherDetails.setAccountHead(accountMapping.getLedgerAccHeadCode());
            voucherDetails.setCreditAcc(accountMapping.getLedgerAccount().getAccountNo());
        }
        return voucherDetails;
    }

    @Override
    public VouchersDto createLoanReceiptVoucher(LoanDetails loanDetails, RepaymentDto repaymentDto) {

        VoucherType paymentVoucher = voucherTypeService.getVoucherTypeByName(AccountConstants.RECEIPTS);
        String loanType=loanDetails.getLoanType();
        String type = null;
        if(loanType.equals((LoanConstants.SHORT_TERM_LOAN))){
        	 type="ST";
        }
        if(loanType.equals(LoanConstants.MID_TERM_LOAN)) {
        	type="MT";
        }
        if(loanType.equals(LoanConstants.LONG_TERM_LOAN)) {
        	type="LT";
        }
        String loanNarration = "Being interest repaid in "+type +" loan no " + loanDetails.getLoanAccountNo();
        String loanNarration1 = "Being interest and principal repaid in "+type +" loan no " + loanDetails.getLoanAccountNo();
       
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
        vouchers.setNarration(loanNarration);
        vouchers.setAppCode(AccountConstants.LOAN);
        //
        Set<VoucherDetails> voucherDetailsSet = new HashSet<>();

        // Create debit transaction entry for cash given
        VoucherDetails cashVoucherDetails = createCashEntry(repaymentDto.getInstallmentAmt(), true );
        voucherDetailsSet.add(cashVoucherDetails);

        if(repaymentDto.getInterestPaid() > 0){
            boolean isOverdue=false;
            if (repaymentDto.getPenalty() > 0){
                isOverdue=true;
            }
            VoucherDetails loanInterestVoucherDetails = createLoanInterestEntry(repaymentDto.getInterestPaid(), isOverdue, loanType );
            voucherDetailsSet.add(loanInterestVoucherDetails);
        }
        if(repaymentDto.getPrinciplePaid() > 0){
            VoucherDetails loanPrincipalVoucherDetails = createLoanPrincipalEntry(loanDetails,repaymentDto.getPrinciplePaid(), false );
            voucherDetailsSet.add(loanPrincipalVoucherDetails);
            vouchers.setNarration(loanNarration1);

        }
        vouchers.setVoucherDetails(voucherDetailsSet);
        return vouchersMapper.toDto(vouchers);
    }

    public VouchersDto updateRepaymentVoucher(VouchersDto vouchersDto){
        VoucherType voucherType = voucherTypeRepository.getOne(vouchersDto.getVoucherTypeId());
        Vouchers vouchers = vouchersMapper.toDomain(vouchersDto);
        vouchers.setVoucherType(voucherType);
        vouchersRepository.save(vouchers);
        return vouchersMapper.toDto(vouchers);
    }

    @Override
    public Vouchers createDisbursementVoucher(LoanDetails loanDetails, Disbursement disbursement) {
        return createLoanPaymentReceipt(loanDetails,disbursement.getDisbursementAmt());
    }
}
