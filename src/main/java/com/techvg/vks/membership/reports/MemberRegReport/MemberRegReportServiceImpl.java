package com.techvg.vks.membership.reports.MemberRegReport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.techvg.vks.deposit.domain.DepositLedger;
import com.techvg.vks.deposit.domain.SavingAccount;
import com.techvg.vks.deposit.domain.SavingInterest;
import com.techvg.vks.deposit.reports.SavingsAccountLedger.SavingAccountReportServiceImpl;
import com.techvg.vks.deposit.repository.DepositLedgerRepository;
import com.techvg.vks.deposit.repository.SavingAccountRepository;
import com.techvg.vks.deposit.repository.SavingInterestRepository;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberRegReportServiceImpl implements MemberRegReportService{

	private final MemberRepository memberRepository;
	private static final String FILEPATH_REPORT = "/MembershipRegister.jrxml";
    private final SavingAccountRepository savingAccountRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public ResponseEntity<?> getMemberRegList(Long memberId) {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF)
				.body(getMemberRegReport(memberId));
	}
	
	
	private byte[] getMemberRegReport(Long memberId) {
		File resource = null;
		byte[] reportblob = null;
		Connection conn = null;

		try {
			resource = new ClassPathResource(FILEPATH_REPORT).getFile();

		} catch (IOException e) {
			throw new NotFoundException("File Not Found...");
		}

		Optional<SavingAccount> savingAccount = savingAccountRepository.findByMemberIdAndStatus(memberId, "A");

		Optional<Member> member = memberRepository.findById(memberId);
        if(member.isPresent()) {


		ArrayList<Member> memberList = new ArrayList<>();
		memberList.add(member.get());

		try {
			conn = jdbcTemplate.getDataSource().getConnection();

			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));

			Map<String, Object> params = new HashMap<>();
			
			params.put("memberId", member.get().getId());
//
		member.get().getNominees().forEach(action -> {

				params.put("nomineeFirstName",
						action.getFirstName() + " " + action.getMiddleName() + " " + action.getLastName()+", "+action.getNomineePermanentAddr());
				params.put("nomineeRelation", action.getNomineeRelation());
				params.put("dob", action.getDob());
				params.put("nominationDate", action.getNomineeDeclareDate());

				params.put("guardian", action.getGuardianName() + "," + action.getParentPermanentAddr());
			});
		
			member.get().getHouse().forEach(action -> {
				if (action.getAddressType().contentEquals("PERMANENT")) {
				params.put("address", action.getAddressLine1() + ", " + action.getAddressLine2() + ", "
						+ action.getCity() + ", " + action.getState() + ", PIN - " + action.getPincode());
				params.put("value", action.getValuation());
				params.put("area", action.getHouseArea());
				params.put("no", action.getHouseNumber());

				}
			});
//			member.get().getMemberBelonging().forEach(action -> {
//				params.put("belType",action.getBelongingType());
//				params.put("belNo.",action.getCount());
//				params.put("belValue", action.getAmount());
//			
//			});
			member.get().getLand().forEach(action -> {
				params.put("land",action.getLandAreaHector());
				
			
			});
//			member.get().getShares().forEach(action -> {
//				params.put("shareAmount",action.getShareAmount());
//			
//			});
//			
//			savingAccount.get().getDepositLedger().forEach(action -> {
//				params.put("depositeAmt",action.getBalanceAmount());
//			
//			});
//			
//			member.get().getLoanDetails().forEach(action -> {
//				params.put("borrowingAmt",action.getLoanAmt());
//			
//			});
			
			params.put("date",new Date());

			JasperPrint print = JasperFillManager.fillReport(report, params, conn);

			reportblob = JasperExportManager.exportReportToPdf(print);

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return reportblob;
	}else {
        throw new NotFoundException("Member not found: " +memberId );

	}
	}

}


