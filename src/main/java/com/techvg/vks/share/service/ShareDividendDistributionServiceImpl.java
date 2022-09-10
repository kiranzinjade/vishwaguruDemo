package com.techvg.vks.share.service;


import com.techvg.vks.config.ShareConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.share.domain.SharesAllocation;
import com.techvg.vks.share.repository.SharesAllocationRepository;
import com.techvg.vks.society.domain.SocietyConfiguration;
import com.techvg.vks.society.repository.SocietyConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShareDividendDistributionServiceImpl implements ShareDividendDistributionService {

	private final MemberRepository memberRepository;
	private final SocietyConfigurationRepository societyConfigurationRepository;
	private final SharesAllocationRepository sharesAllocationRepository;
	private Integer memberShareCount = 0;
	private Double singleShareDividentValue = 0.0;

	private Boolean memberIsNpaOrDefaulter;
	private Boolean isNpa;
	private Boolean isDefaulter;

	private void depositeToMembersSavingsAccount(Double totalShareDividendAmount, Member member) {

		// Savings Account of the member will be done here in the method
	}

	private void depositeToMembersLoanAccount(Double totalShareDividendAmount, Member member) {

		// Deposite to the all NPA members loan account starts here
	}

	private Double getPerShareDividendAmount() {
// fetching the previously calculated per share dividend amount.
		return societyConfigurationRepository.findByConfigName(ShareConstants.ONE_SHARE_PRICE)
				.orElseThrow(NotFoundException::new).getValue();
	}

	private Integer findTotalNoOfShares(Member member) {

		// finding the total number of the shares before the current year of the march
		memberShareCount = 0;
		List<SharesAllocation> membersSharesAllocation = sharesAllocationRepository
				.findByMemberAndAllocationDateBefore(member, getMaxDateForShareCount());
		membersSharesAllocation.forEach(action -> {
			memberShareCount = memberShareCount + action.getNoOfShares();
		});

		return memberShareCount;
	}

	private Date getMaxDateForShareCount() {
		// Getting latest date for the share count limit of the member
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MONTH, Calendar.MARCH);
		cal.set(Calendar.DATE, 31);
		return cal.getTime();
	}

	private Boolean isNpa(String loanClassification) {
		// return if the member is Npa or not

		return (loanClassification.contentEquals(ShareConstants.MEMBER_NPA_SUBSTANDARD)
				|| loanClassification.contentEquals(ShareConstants.MEMBER_NPA_DOUBTFUL1)
				|| loanClassification.contentEquals(ShareConstants.MEMBER_NPA_DOUBTFUL2)
				|| loanClassification.contentEquals(ShareConstants.MEMBER_NPA_DOUBTFUL3));
	}

	private Boolean isDefaulter(String loanClassification) {
		// return if the member is Npa or not
		return (loanClassification.contentEquals(ShareConstants.MEMBER_DEFAULTER));
	}

	private List<Member> filterDefaulterMembers(List<Member> memberList) {
		final List<Member> nonDefaulterMembers = new ArrayList<>();

		memberList.forEach(member -> {
			isDefaulter = false;
			member.getLoanDetails().forEach(loanDetail -> {

				if (isDefaulter(loanDetail.getLoanClassification())) {
					isDefaulter = true;
				}
			});

			if (!isDefaulter) {
				nonDefaulterMembers.add(member);
			}
		});

		return nonDefaulterMembers;
	}

	private List<Member> filterNpaMembers(List<Member> memberList) {
		final List<Member> nonNpaMembers = new ArrayList<>();

		memberList.forEach(member -> {
			isNpa = false;
			member.getLoanDetails().forEach(loanDetail -> {

				if (isNpa(loanDetail.getLoanClassification())) {
					isNpa = true;
				}
			});

			if (!isNpa) {
				nonNpaMembers.add(member);
			}
		});

		return nonNpaMembers;
	}

	private Boolean distributeShareDividend(List<Member> memberList, Boolean loanAccountPay) {
		try {
			
			singleShareDividentValue = getPerShareDividendAmount();// getting the single share dividend value
			memberList.forEach(member -> {
				// traversing from the member's master list
				memberIsNpaOrDefaulter = false; // resetting flag per iteration
				// calculating total dividend amount for each member
				Integer totalNumberOfShares = findTotalNoOfShares(member);
				Double totalShareDividendAmount = singleShareDividentValue * totalNumberOfShares;
				// ====================end of calculation

				if (member.getLoanDetails() != null) {
					// if the loan details is not null then member has taken the loan
					// ========================== traverse through members multiple loan database
					// where to identify the member is defaulter, npa or regular
					member.getLoanDetails().forEach(loanDetail -> {
						if (isNpa(loanDetail.getLoanClassification())
								|| isDefaulter(loanDetail.getLoanClassification())) {
							memberIsNpaOrDefaulter = true; // if the member is defaulter or NPA this flag will set to
															// true
						}
					});// ============End of traversal with flag setting===========================

					// if the member is npa or defaulter then check the loanAccountPay flag which
					// decides the target account types(loan or savings)
					if (memberIsNpaOrDefaulter) {
						if (loanAccountPay) {// if the loanAccountPay is active then dividend will be deposited to
												// member's loan account
							depositeToMembersLoanAccount(totalShareDividendAmount, member);
						} else { // if the loanAccountPay is false then dividend will be deposited to Defaulters
									// Or Npa member's savings account
							depositeToMembersSavingsAccount(totalShareDividendAmount, member);
						}

					} else {// if the member is neither npa nor defaulter then must be standard / regular
							// then for such members share dividend transfer within their savings account
						depositeToMembersSavingsAccount(totalShareDividendAmount, member);
					}

				} else {
					// if the loan details is null then the member has not taken the loan but may
					// holds some shares
					// for such members dividend amount will be transferred to their savings account
					depositeToMembersSavingsAccount(totalShareDividendAmount, member);
				}
			});
			// if all distribution happens correctly then it will return true to indicate
			// success.
			return true;
		} catch (Exception e) {
			log.error("Error in Deposit Process" + e.getMessage());
			return false;
			// this block will throw Exception in any technical obstacle and return false.
		}
	}

	@Override
	public String transferShareDivident() {
		// get all active members List
		List<Member> memberList = memberRepository.findByStatus("A");
		// get All Parameters Value Required for the calculation and decision making.
		List<SocietyConfiguration> societyConfiguration = societyConfigurationRepository
				.findByConfigType(ShareConstants.CONFIG_TYPE_SHAREDIVIDEND);
		final Map<String, Object> configurationMap = new HashMap<>();
		societyConfiguration.forEach(configuration -> {

			configurationMap.put(configuration.getConfigName(), configuration.getValue());

		});

		if ((boolean) configurationMap.get(ShareConstants.NOT_PAY_NPA)) {
			// if the Not to Pay Npa flag is set then this block will omit the npa members
			// from the master list
			// memberlist will get Non Npa Members ;
			memberList = filterNpaMembers(memberList);
		}

		if ((boolean) configurationMap.get(ShareConstants.NOT_PAY_DEFAULTER)) {
			// if the Not to Pay Defaulter flag is set then this block will omit the
			// Defaulter members from the master list
			// memberlist will get Non Defaulter Members
			memberList = filterDefaulterMembers(memberList);
		}

		if (distributeShareDividend(memberList,
				(Boolean) configurationMap.get(ShareConstants.PAY_NPA_DEFAULTERS_TO_LOAN_ACCOUNT))) {
			return "Share Dividend For Members Transfered Successfully";
		} else {
			return "Error in dividend deposit process";
		}

	}
}
