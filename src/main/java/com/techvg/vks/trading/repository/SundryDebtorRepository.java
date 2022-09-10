package com.techvg.vks.trading.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.techvg.vks.trading.domain.SundryDebtor;

@Repository
public interface SundryDebtorRepository extends JpaRepository<SundryDebtor, Long>{

	@Query(value = "SELECT * FROM sundry_debtor WHERE member_id =:memberId ORDER BY sundry_debtor.id DESC LIMIT 1",nativeQuery = true)
	SundryDebtor findLastRecordById(@Param("memberId") Long memberId);
	
	@Query(value="select s from SundryDebtor s where s.member.id=:memberId ")
	List<SundryDebtor> findByMemberId(@Param("memberId") Long memberId);
	
	Page<SundryDebtor> findByisDeleted(Pageable pageable,boolean deleted);

}
