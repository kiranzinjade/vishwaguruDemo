package com.techvg.vks.share.repository;

import com.techvg.vks.share.domain.Shares;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SharesRepository extends JpaRepository<Shares, Long> {
	
	Optional<Shares> findById(Long shareApplicationId);

	/*Page<Shares> findByStatus(String status, Pageable pageable);

	List<Shares> findByStatus(String status);*/
	
	@Query(value="select s from Shares s where s.member.id=:memberId ")
	List<Shares> findByMemberId(@Param("memberId") Long memberId);

	Page<Shares> findByisDeleted(boolean b, Pageable pageable);

	@Query(value="select sum(share_application.share_amount) from share_application where share_application.share_application_status='accepted'",nativeQuery=true)
	double getShareAmount();
}



