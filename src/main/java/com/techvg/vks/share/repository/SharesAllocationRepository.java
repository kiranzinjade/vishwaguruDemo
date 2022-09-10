package com.techvg.vks.share.repository;

import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.share.domain.SharesAllocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SharesAllocationRepository extends JpaRepository<SharesAllocation, Long> {

	Optional<SharesAllocation> findById(Long memberId);

	/*Page<SharesAllocation> findByStatus(String status, Pageable pageable);

	List<SharesAllocation> findByStatus(String status);*/

	List<SharesAllocation> findByMember(Member member);

	List<SharesAllocation> findByMemberAndAllocationDateBefore(Member member,Date date);
	
	@Query(value="select s from SharesAllocation s where s.member.id=:memberId and s.sharesAllocationStatus='allocated' ")
	List<SharesAllocation> findByMemberId(@Param("memberId") Long memberId);

	@Query(value="select max(s.sharesIdTo) from SharesAllocation s ")
	Integer findByLastRecord();

	@Query(value="select s from SharesAllocation s where s.member.id=:memberId")
	List<SharesAllocation> findSharesId(Long memberId);

	Page<SharesAllocation> findByisDeleted(boolean b, Pageable pageable);

}
