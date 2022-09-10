package com.techvg.vks.share.service;

import org.springframework.stereotype.Service;

import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.share.domain.SharesAllocation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
public interface ShareCertificateService {
byte[] generateMemberCertificate(SharesAllocation shareAllocation,Double shareCapital,Integer totalShares,Double singleShareValue);

byte[] getMemberCertificateById(Long shareAllocationId);


}
