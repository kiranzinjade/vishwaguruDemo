package com.techvg.vks.membership.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.techvg.vks.membership.domain.Land;

@Repository
public interface LandRepository extends JpaRepository<Land,Long> {
 
		Land findById(int Long);
		
		@Query(value="select s from Land s where s.member.id=:memberId ")
		List<Land> findByMemberId(@Param("memberId") Long memberId);
	
}

