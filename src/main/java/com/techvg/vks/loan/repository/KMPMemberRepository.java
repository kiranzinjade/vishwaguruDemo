package com.techvg.vks.loan.repository;

import com.techvg.vks.loan.domain.KMPMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KMPMemberRepository extends JpaRepository<KMPMember,Long>  {

	KMPMember findByMember_IdAndKmpYear(Long memberId, int year);

	List<KMPMember> findByKmpYear(int year);
}
