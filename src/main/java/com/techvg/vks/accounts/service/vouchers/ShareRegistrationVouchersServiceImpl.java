package com.techvg.vks.accounts.service.vouchers;

import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.domain.VoucherDetails;
import com.techvg.vks.accounts.domain.VoucherType;
import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.accounts.repository.LedgerAccountsRepository;
import com.techvg.vks.accounts.repository.VouchersRepository;
import com.techvg.vks.accounts.service.VoucherTypeService;
import com.techvg.vks.common.LoginUser;
import com.techvg.vks.common.SocietyConfig;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.config.ShareConstants;
import com.techvg.vks.idgenerator.VoucherSeq;
import com.techvg.vks.idgenerator.repository.VoucherSeqRepository;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.model.MemberDto;
import com.techvg.vks.share.domain.MemberShareAcc;
import com.techvg.vks.share.domain.Shares;
import com.techvg.vks.share.domain.SharesAllocation;
import com.techvg.vks.share.service.SharesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Service
@RequiredArgsConstructor
public class ShareRegistrationVouchersServiceImpl implements ShareRegistrationVouchersService {

    private final VoucherTypeService voucherTypeService;
    private final LedgerAccountsRepository ledgerAccountsRepository;
    private final VouchersRepository vouchersRepository;
    private final VoucherSeqRepository voucherSeqRepository;
    private final SharesService sharesService;

    @Override
    public Vouchers createMemberShareRegistrationReceiptVoucher(MemberDto memberDto, Long memberId, int useSuspenseAcc) {

        double registrationFee;
        int shareCount = memberDto.getNoOfShare();
        double shareAmt = shareCount * SocietyConfig.getValue(ShareConstants.SINGLE_SHARE_PRICE);

        VoucherType receiptVoucher = voucherTypeService.getVoucherTypeByName(AccountConstants.RECEIPTS);
        registrationFee = memberDto.getRegistrationFee();
        double totalAmt = shareAmt + registrationFee;

        String shareNarration = "Being allotment of shares of \n" +
                memberDto.getFirstName() + " " + memberDto.getLastName() + "\n" +
                "No. of shares " + shareCount;

        String regNarration = "Being allotment of shares and \n" +
                "admission fee of \n" +
                memberDto.getFirstName() + " " + memberDto.getLastName() + "\n" +
                "Admission fee " + registrationFee +"\n" +
                "No. of shares " + shareCount;

        // Create Voucher No from sequence
        VoucherSeq no = new VoucherSeq();
        voucherSeqRepository.save(no);
        Long voucherNo = no.getVoucherNo();

        Vouchers vouchers = new Vouchers();
        vouchers.setVoucherDate(new Date());
        vouchers.setPreparedBy(LoginUser.getUser());
        vouchers.setVoucherType(receiptVoucher);
        vouchers.setMode(AccountConstants.CASH);
        vouchers.setVoucherNo(voucherNo);
        vouchers.setNarration(shareNarration);
        vouchers.setAppCode(AccountConstants.SHARE);

        Set<VoucherDetails> voucherDetailsSet = new HashSet<>();

        // Create debit transaction entry for cash received
        VoucherDetails cashVoucherDetails = createCashEntry(totalAmt,true);

        // Create credit trans entry for share amount
        VoucherDetails shareVoucherDetails = createShareAllocation(shareAmt, useSuspenseAcc, memberId);

        voucherDetailsSet.add(cashVoucherDetails);
        voucherDetailsSet.add(shareVoucherDetails);

        // Create credit trans  entry for registration fee
        if(registrationFee > 0.0){
            VoucherDetails admissionFeeVoucherDetails = createRegistrationFee(registrationFee);
            voucherDetailsSet.add(admissionFeeVoucherDetails);
            vouchers.setNarration(regNarration);
        }

        vouchers.setVoucherDetails(voucherDetailsSet);
        vouchersRepository.save(vouchers);
        return vouchers;
    }

    private VoucherDetails createCashEntry(double totalAmt, boolean isDebit){
        LedgerAccounts cashOnHandAcc = ledgerAccountsRepository.findByAccHeadCode(AccountConstants.CASH_ON_HAND);

        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(totalAmt);
        if(isDebit)
            voucherDetails.setDebitAcc(cashOnHandAcc.getAccountNo());
        else
            voucherDetails.setCreditAcc(cashOnHandAcc.getAccountNo());
        voucherDetails.setAccountHead(cashOnHandAcc.getAccHeadCode());
        return voucherDetails;
    }

    private VoucherDetails createRegistrationFee(double registrationFee){
        VoucherDetails voucherDetails = new VoucherDetails();

        // Get Admission Fee a/c
        LedgerAccounts admissionFeeAcc = ledgerAccountsRepository.findByAccHeadCode(AccountConstants.ACCOUNT_FEE);
        Long admissionFeeAccNo = admissionFeeAcc.getAccountNo();

        //create trans for member registration fee
        voucherDetails.setTransAmount(registrationFee);
        voucherDetails.setCreditAcc(admissionFeeAccNo);
        voucherDetails.setAccountHead(admissionFeeAcc.getAccHeadCode());

        return voucherDetails;
    }

    private VoucherDetails createShareAllocation(double shareAmt, int useSuspenseAcc,
                                                 Long memberId){
        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(shareAmt);

        if(useSuspenseAcc == 1){
            // Get Suspense Share a/c
            LedgerAccounts suspenseAcc = ledgerAccountsRepository.findByAccHeadCode(AccountConstants.SHARE_SUSPENSE);
            voucherDetails.setCreditAcc(suspenseAcc.getAccountNo());
            voucherDetails.setAccountHead(suspenseAcc.getAccHeadCode());
        }else{
            // Get Individual Share a/c
            MemberShareAcc shareAcc = sharesService.getMemberShareAcc(memberId);
            voucherDetails.setCreditAcc(shareAcc.getShareAccNumber());
            voucherDetails.setAccountHead(shareAcc.getShareAccName());
        }
        return voucherDetails;
    }

    @Override
    public Vouchers createShareAllocationReceiptVoucher(LoanDetails loanDetails, Shares newShare) {

        VoucherType receiptVoucher = voucherTypeService.getVoucherTypeByName(AccountConstants.RECEIPTS);

        String shareNarration = "Being allotment of shares from loan no " + loanDetails.getLoanAccountNo() +
                "\nfor " + loanDetails.getMember().getFirstName() + " " + loanDetails.getMember().getLastName() + "\n" +
                "No. of shares " + newShare.getNumberOfShares();

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
        vouchers.setNarration(shareNarration);
        vouchers.setAppCode(AccountConstants.SHARE);

        Set<VoucherDetails> voucherDetailsSet = new HashSet<>();

        //Get Member share account
        MemberShareAcc shareAcc = loanDetails.getMember().getMemberShareAcc();
        // Create credit transaction entry for member share acc
        VoucherDetails shareAccVoucherDetails = createShareEntry(shareAcc.getShareAccNumber(), newShare.getShareAmount(),shareAcc.getShareAccName(), false );
        // Create debit trans entry for member loan acc
        VoucherDetails loanAccVoucherDetails = createLoanEntry(loanDetails.getLoanAccountNo(), newShare.getShareAmount(), loanDetails.getLoanAccName());

        voucherDetailsSet.add(shareAccVoucherDetails);
        voucherDetailsSet.add(loanAccVoucherDetails);

        vouchers.setVoucherDetails(voucherDetailsSet);
        vouchersRepository.save(vouchers);
        return vouchers;
    }

    @Override
    public Vouchers createShareRefundPaymentVoucher(double shareAmt, Member member) {
        VoucherType paymentVoucher = voucherTypeService.getVoucherTypeByName(AccountConstants.PAYMENT);

        String shareNarration = "Being refund of share amount for " +  member.getFirstName() + " " + member.getLastName() + "\n" +
                "No. of shares " + member.getSharesAllocation().stream().mapToInt(SharesAllocation::getNoOfShares).sum();

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
        vouchers.setNarration(shareNarration);
        vouchers.setAppCode(AccountConstants.SHARE);

        Set<VoucherDetails> voucherDetailsSet = new HashSet<>();

        //Get Member share account
        MemberShareAcc shareAcc = member.getMemberShareAcc();
        // Create debit transaction entry for member share acc for share refund
        VoucherDetails shareAccVoucherDetails = createShareEntry(shareAcc.getShareAccNumber(), shareAmt,shareAcc.getShareAccName(), true );
        // Create credit transaction entry for cash given
        VoucherDetails cashVoucherDetails = createCashEntry(shareAmt,false);

        voucherDetailsSet.add(shareAccVoucherDetails);
        voucherDetailsSet.add(cashVoucherDetails);

        vouchers.setVoucherDetails(voucherDetailsSet);
        vouchersRepository.save(vouchers);
        return vouchers;
    }

    private VoucherDetails createShareEntry(Long memShareAccNo, double totalAmt, String accHead, boolean isDebit){
        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(totalAmt);
        if(isDebit)
            voucherDetails.setDebitAcc(memShareAccNo);
        else
            voucherDetails.setCreditAcc(memShareAccNo);
        voucherDetails.setAccountHead(accHead);
        return voucherDetails;
    }

    private VoucherDetails createLoanEntry(Long memLoanAccNo, double totalAmt, String accHead){
        VoucherDetails voucherDetails = new VoucherDetails();
        voucherDetails.setTransAmount(totalAmt);
        voucherDetails.setDebitAcc(memLoanAccNo);
        voucherDetails.setAccountHead(accHead);
        return voucherDetails;
    }


}
