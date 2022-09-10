package com.techvg.vks.membership.service;

import com.techvg.vks.config.MemberConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Land;
import com.techvg.vks.membership.domain.LandDocument;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.mapper.LandMapper;
import com.techvg.vks.membership.model.LandDto;
import com.techvg.vks.membership.model.LandPageList;
import com.techvg.vks.membership.repository.LandDocumentRepository;
import com.techvg.vks.membership.repository.LandRepository;
import com.techvg.vks.membership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class LandServiceImpl implements LandService {

	private final LandRepository landRepository;
	private final LandMapper landmapper;
	private final LandDocumentRepository landDocumentRepository;
	private final MemberRepository memberRepository;

	@Override
	public ResponseEntity<Object> addLandInfo(String[] landType, Integer[] landAreaHector, Integer[] landAreaR, String[] landGatno,
			MultipartFile[] saatBara, MultipartFile[] eightA, MultipartFile[] jindagiPatrak, Long memberId,
			Authentication authentication) throws IOException {
		
		Member member = memberRepository.findById(memberId).orElseThrow(
				() -> new NotFoundException("No Member  found for id : " +memberId));
		for (int i = 0; i < saatBara.length; i++) {

			Land landdetails = new Land();
			MultipartFile file = saatBara[i];
			MultipartFile file1 = eightA[i];
			MultipartFile file2 = jindagiPatrak[i];
			String saatBaraFileName = file.getOriginalFilename();
			String eightAFileName = file1.getOriginalFilename();
			String jindagiPatrakFileName = file2.getOriginalFilename();
			String landTypes = landType[i];
			String landGatnos = landGatno[i];
			Integer landAreasHector = landAreaHector[i];
			Integer landAreasR = landAreaR[i];
			landdetails.setLandAreaHector(landAreasHector);
			landdetails.setLandAreaR(landAreasR);
			landdetails.setLandGatno(landGatnos);
			landdetails.setLandType(landTypes);
			landdetails.setIsDeleted(false);
			landdetails.setMember(member);
			
			landRepository.save(landdetails);
			
			LandDocument landDocument = new LandDocument();
			landDocument.setSaatBarafileName(saatBaraFileName);
			landDocument.setEightAfileName(eightAFileName);
			landDocument.setJindagiPatrakfileName(jindagiPatrakFileName);
			landDocument.setSaatBara(file.getBytes());
			landDocument.setEightA(file1.getBytes());
			landDocument.setJindagiPatrak(file2.getBytes());
			landDocument.setId(landdetails.getId());
			landDocument.setIsDeleted(false);
			landDocumentRepository.save(landDocument);

		}

		return null;
	}

	@Override
	public LandDto addNewLand(LandDto lanDto, MultipartFile[] saatBara, MultipartFile[] eightA,
			MultipartFile[] jindagiPatrak, Authentication authentication) {

		return null;
	}

	@Override
	public ResponseEntity<Object> updateLandInfo(String[] landType,  Integer[] landAreaHector, Integer[] landAreaR, String[] landGatno,
			MultipartFile[] saatBara, MultipartFile[] eightA, MultipartFile[] jindagiPatrak, Long memberId,
			Authentication authentication) throws IOException {
		
       Member member = memberRepository.findById(memberId).orElseThrow(
       		() -> new NotFoundException("No Member  found for id : " +memberId));
       
		
		for (int i = 0; i < saatBara.length; i++) {
			Land saveLand = null;
			Land landdetails = new Land();
			MultipartFile file = saatBara[i];
			MultipartFile file1 = eightA[i];
			MultipartFile file2 = jindagiPatrak[i];
			String saatBaraFileName = file.getOriginalFilename();
			String eightAFileName = file1.getOriginalFilename();
			String jindagiPatrakFileName = file2.getOriginalFilename();
			String landTypes = landType[i];
			String landGatnos = landGatno[i];
			Integer landAreasHector = landAreaHector[i];
			Integer landAreasR = landAreaR[i];
			landdetails.setLandAreaHector(landAreasHector);
			landdetails.setLandAreaR(landAreasR);
			landdetails.setLandGatno(landGatnos);
			landdetails.setLandType(landTypes);
			landdetails.setIsDeleted(false);
			landdetails.setMember(member);
			
			saveLand=landRepository.save(landdetails);
			
			LandDocument landDocument = new LandDocument();
			landDocument.setSaatBarafileName(saatBaraFileName);
			landDocument.setEightAfileName(eightAFileName);
			landDocument.setJindagiPatrakfileName(jindagiPatrakFileName);
			landDocument.setSaatBara(file.getBytes());
			landDocument.setEightA(file1.getBytes());
			landDocument.setJindagiPatrak(file2.getBytes());
			landDocument.setId(saveLand.getId());
			landDocument.setIsDeleted(false);
			landDocumentRepository.save(landDocument);

		}

		return null;
	}

	@Override
	public LandPageList listLand(Pageable pageable) {
		return null;
	}

	@Override
	public LandDto getLandById(Long id) {
		log.debug("REST request to getLand : {}", id);
		 Land land =landRepository.findById(id).orElseThrow(
				 () -> new NotFoundException("No Land  found for id : " +id));
		return landmapper.landToLandDto(land);
	}

	@Override
	public LandDto deleteLandById(Long id) {
		Land land =landRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Land  found for id : " +id));
		if (land != null) {
		land.setIsDeleted(true);
		landRepository.save(land);
    } 
	return landmapper.landToLandDto(land);

	}

	@Override
	public List<LandDto> listLands(Long memberId) {
		List<Land> land=landRepository.findByMemberId(memberId);
		land.forEach(action->{
			action.setEightAFile(null);
			action.setJindagiPatrakFile(null);
			action.setSaatBaraFile(null);
		});
		return landmapper.toDtoList(land);
	}

	@Override
	public LandDto addNewland(LandDto landDto, MultipartFile jindagiPatrakFile, MultipartFile eightAFile,
			MultipartFile saatBaraFile) {

		Land land = landmapper.landDtoToLand(landDto);

		try {
			
			
			Member member = memberRepository.findById(landDto.getMemberId()).orElseThrow(
					() -> new NotFoundException("No Member  found for id : " +landDto.getMemberId()));
			land.setJindagiPatrakFile(jindagiPatrakFile.getBytes());
			land.setJindagiPatrakName(jindagiPatrakFile.getOriginalFilename());
			
			land.setEightAFile(eightAFile.getBytes());
			land.setEightAName(eightAFile.getOriginalFilename());
			
			land.setSaatBaraFile(saatBaraFile.getBytes());
			land.setSaatBaraName(saatBaraFile.getOriginalFilename());
			land.setMember(member);
			
			land.setLandType(landDto.getLandType());
			land.setLandGatno(landDto.getLandGatno());

			land.setLandAreaHector(landDto.getLandAreaHector());

			land.setLandAreaR(landDto.getLandAreaR());

			land.setJindagiPatrakNo(landDto.getJindagiPatrakNo());

			land.setJindagiAmount(landDto.getJindagiAmount());
			land.setLandAddress(landDto.getLandAddress());
			land.setValueOfProperty(landDto.getValueOfProperty());
			land.setAssigneeOfLand(landDto.getAssigneeOfLand());

			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return landmapper.landToLandDto(landRepository.save(land));
	}

	@Override
	public LandDto listLandDetails(Long id, String fileName) {
		Land land=landRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Member  found for id : " +id));

		System.out.println("saat bara ----------------------"+land.getSaatBaraName());
		if(fileName.equals(MemberConstants.SAATBARA_FILE)) {
			land.setEightAFile(null);
			land.setJindagiPatrakFile(null);
		}
		else if(fileName.equals(MemberConstants.EIGHTA_FILE)) {
			land.setSaatBaraFile(null);
			land.setJindagiPatrakFile(null);
		}
		else if(fileName.equals(MemberConstants.JINDAGIPATRAK_FILE)) {
			land.setSaatBaraFile(null);
			land.setEightAFile(null);
		}
		return landmapper.landToLandDto(land);
	}
	


}
