package com.techvg.vks.society.service;

import com.techvg.vks.society.model.AgmAttendanceDto;
import com.techvg.vks.society.model.AgmAttendancePageList;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface AgmAttendanceService {

	AgmAttendanceDto importCsvFile(MultipartFile file, Long agmId) throws IOException;
	
	AgmAttendancePageList listAgmAttendaceMember(Pageable pageable, Long agmId);

	AgmAttendanceDto deleteById(Long agmAttendanceId);

	AgmAttendanceDto findById(Long agmAttendanceId);

	AgmAttendanceDto updateCsvFile(MultipartFile file, Long agmId) throws IOException;

}
