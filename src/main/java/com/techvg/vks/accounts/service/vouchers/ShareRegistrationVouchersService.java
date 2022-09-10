package com.techvg.vks.accounts.service.vouchers;

import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.model.MemberDto;
import com.techvg.vks.share.domain.Shares;
import org.springframework.stereotype.Service;

@Service
public interface ShareRegistrationVouchersService {

    Vouchers createMemberShareRegistrationReceiptVoucher(MemberDto memberDto, Long memberId, int useSuspenseAcc);

    Vouchers createShareAllocationReceiptVoucher(LoanDetails loanDetails, Shares newShare);

    Vouchers createShareRefundPaymentVoucher(double shareAmt, Member member);

}
