package com.techvg.vks.membership.reports.namunaj.service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.reports.namunaj.mapper.NamunaJMapper;
import com.techvg.vks.membership.reports.namunaj.model.NamunaJWrapper;
import com.techvg.vks.membership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor

public class NamunaJServiceImpl implements NamunaJService {

	private final MemberRepository memberRepo;
	private final NamunaJMapper namunaJMapper;
	private static final String FILEPATH_NAMUNA_J_1 = "/NAMUNA_J_1.jrxml";
	private static final String FILEPATH_NAMUNA_J_2 = "/NAMUNA_J_2.jrxml";

	@Override
	public byte[] getNamunaJByMemberTypes(String memberType) {
		switch (memberType.toUpperCase()) {
		case ACTIVE:
			log.info("Member Type received (service) successfully = " + ACTIVE);
			return getNamunaJForActiveMember();
		case NONACTIVE:
			log.info("Member Type received (service) successfully = " + NONACTIVE);
			return getNamunaJForNonActiveMember();
		default:
			throw new NotFoundException("Please specify the Member type : Active / Inactive");
		}
	}

	@Override
	public byte[] getNamunaJForActiveMember() {
		File resource = null;
		try {
			resource = new ClassPathResource(FILEPATH_NAMUNA_J_1).getFile();
			log.info("file got for namuna j-1 = " + resource.getName());
		} catch (IOException e) {
			throw new NotFoundException("Report Template for Namuna J-1 Not Found...");
		}
		List<Member> memberList = memberRepo.findByStatus("A");
		List<NamunaJWrapper> namunaJReportWrapper = namunaJMapper.mapActiveMemberDataList(memberList);

		return makeNamunaJReport(resource, namunaJReportWrapper);
	}

	@Override
	public byte[] getNamunaJForNonActiveMember() {
		File resource = null;
		try {
			resource = new ClassPathResource(FILEPATH_NAMUNA_J_2).getFile();
			log.info("file got for namuna j-2 = " + resource.getName());
		} catch (IOException e) {
			throw new NotFoundException("Report Template for Namuna J-2 Not Found");
		}

		List<Member> memberList = memberRepo.findByStatus("I");
		List<NamunaJWrapper> namunaJWrapper = namunaJMapper.mapNonActiveMemberDataList(memberList);

		return makeNamunaJReport(resource, namunaJWrapper);

	}

	private byte[] makeNamunaJReport(File templateResource,
									 List<NamunaJWrapper> namunaJWrapper)  {

		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(namunaJWrapper);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("SocietyName", "Vividh Kaaryakaari Society");
		JasperReport report = null;
		try {
			report = JasperCompileManager.compileReport(new FileInputStream(templateResource));
			JasperPrint print = JasperFillManager.fillReport(report, parameters, beanDataSource);
			return JasperExportManager.exportReportToPdf(print);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotFoundException("Error While Preparing Namuna J Document ! : " +templateResource.getName());
		}

	}
}
