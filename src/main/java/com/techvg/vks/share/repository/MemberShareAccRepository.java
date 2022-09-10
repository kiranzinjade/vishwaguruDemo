package com.techvg.vks.share.repository;

import com.techvg.vks.share.domain.MemberShareAcc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberShareAccRepository extends JpaRepository<MemberShareAcc, Long> {

    Optional<MemberShareAcc> findByMemberId(Long memberId);
    MemberShareAcc findByShareAccNumber(Long accNo);
}
