package com.techvg.vks.society.service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.Agm;
import com.techvg.vks.society.domain.AgmAttendance;
import com.techvg.vks.society.mapper.AgmAttendanceMapper;
import com.techvg.vks.society.model.AgmAttendanceDto;
import com.techvg.vks.society.model.AgmAttendancePageList;
import com.techvg.vks.society.repository.AgmAttendanceRepository;
import com.techvg.vks.society.repository.AgmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgmAttendanceServiceImpl implements AgmAttendanceService {

	private final AgmAttendanceRepository agmAttendanceRepository;
	private final AgmAttendanceMapper agmAttendanceMapper;
	private final AgmRepository agmRepository;

	@Override
	public AgmAttendanceDto importCsvFile(MultipartFile file, Long agmId) throws IOException {

		Agm agm = agmRepository.findById(agmId).orElseThrow(NotFoundException::new);

		byte[] bytes = file.getBytes();
		ByteArrayInputStream inputFilestream = new ByteArrayInputStream(bytes);
		BufferedReader br = new BufferedReader(new InputStreamReader(inputFilestream));
		String line = "";
		String cvsSplitBy = ",";
		br.readLine();
		while ((line = br.readLine()) != null) {

			String[] cols = line.split(cvsSplitBy);
			String memberId = cols[0];
			String memberName = cols[1];
			String attendanceStatus = cols[2];
			AgmAttendance attendance = AgmAttendance.builder().agm(agm).memberId(memberId).memberName(memberName)
					.attendanceStatus(attendanceStatus).build();
			agmAttendanceRepository.save(attendance);
		}

		return null;
	}

	@Override
	public AgmAttendancePageList listAgmAttendaceMember(Pageable pageable,Long agmId) {
		log.debug("Request to get List : {}");

		Page<AgmAttendance> agmAttendancePage;
		
		agmAttendancePage = agmAttendanceRepository.findByIsDeletedAndAgmId(false, pageable, agmId);

		return new AgmAttendancePageList(
				agmAttendancePage.getContent().stream().map(agmAttendanceMapper::agmAttendanceToAgmAttendanceDto)
						.collect(Collectors.toList()),
				PageRequest.of(agmAttendancePage.getPageable().getPageNumber(),
						agmAttendancePage.getPageable().getPageSize()),
				agmAttendancePage.getTotalElements());

	}

	@Override
	@Transactional
	public AgmAttendanceDto updateCsvFile(MultipartFile file, Long agmId) throws IOException {

		Agm agm = agmRepository.findById(agmId).orElseThrow(NotFoundException::new);
		agm.getAgmAttendance().clear();
		agm = agmRepository.saveAndFlush(agm);

		byte[] bytes = file.getBytes();
		ByteArrayInputStream inputFilestream = new ByteArrayInputStream(bytes);
		BufferedReader br = new BufferedReader(new InputStreamReader(inputFilestream));
		String line = "";
		String cvsSplitBy = ",";

		while ((line = br.readLine()) != null) {

			String[] cols = line.split(cvsSplitBy);
			String memberId = cols[0];
			String memberName = cols[1];
			String attendanceStatus = cols[2];

			AgmAttendance attendance = AgmAttendance.builder()
					.agm(agm)
					.memberId(memberId)
					.memberName(memberName)
					.attendanceStatus(attendanceStatus)
					.build();
			agm.getAgmAttendance().add(attendance);
		}

		agmRepository.saveAndFlush(agm);
		return null;
	}

	@Override
	public AgmAttendanceDto deleteById(Long agmAttendanceId) {

		AgmAttendance agmAttendance = agmAttendanceRepository.findById(agmAttendanceId)
				.orElseThrow(NotFoundException::new);

		if (agmAttendance != null) {

			agmAttendance.setIsDeleted(true);
			agmAttendanceRepository.save(agmAttendance);

		}

		return null;
	}

	@Override
	public AgmAttendanceDto findById(Long agmAttendanceId) {

		AgmAttendance agmAttendance = agmAttendanceRepository.findById(agmAttendanceId)
				.orElseThrow(NotFoundException::new);

		return agmAttendanceMapper.agmAttendanceToAgmAttendanceDto(agmAttendance);

	}

}