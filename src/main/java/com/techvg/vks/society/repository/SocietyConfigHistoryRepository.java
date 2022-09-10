package com.techvg.vks.society.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techvg.vks.society.domain.SocietyConfigHistory;
import com.techvg.vks.society.domain.SocietyConfiguration;

@Repository
public interface SocietyConfigHistoryRepository extends JpaRepository<SocietyConfigHistory, Long> {

	Page<SocietyConfigHistory> findByStatus(String status, Pageable pageable);

	List<SocietyConfigHistory> findByConfigType(String configType);

	@Query(value="select s.value from SocietyConfigHistory s where s.configName=:dividendPerShare and s.year=:year")
	double findDividendPerShareByYear(@Param("dividendPerShare") String dividendPerShare ,@Param("year") int year);

}
