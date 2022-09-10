package com.techvg.vks.share.controller;

import com.techvg.vks.share.service.ShareCertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/share/certificate")
@CrossOrigin(origins="*")
@RequiredArgsConstructor

public class ShareCertificateController {
private final ShareCertificateService shareCertificateService;

@GetMapping({ "/{number}" })
public byte[] getShareCertificate(@PathVariable("number") Long shareAllocationId)
{ 	
return shareCertificateService.getMemberCertificateById(shareAllocationId);
}
	
	
	
}

