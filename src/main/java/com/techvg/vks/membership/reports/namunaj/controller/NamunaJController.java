package com.techvg.vks.membership.reports.namunaj.controller;

import com.techvg.vks.membership.reports.namunaj.service.NamunaJService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class NamunaJController {

	private final NamunaJService namunaJService;

	@GetMapping("/namunaj")
	public ResponseEntity<?> getNamunaJ(@RequestParam("membertype") String memberType) {

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF)
				.body(namunaJService.getNamunaJByMemberTypes(memberType));
	}

}
