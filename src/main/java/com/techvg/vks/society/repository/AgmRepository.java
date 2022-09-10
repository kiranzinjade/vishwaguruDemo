package com.techvg.vks.society.repository;

import com.techvg.vks.society.domain.Agm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgmRepository extends JpaRepository<Agm, Long> {


	Optional<Agm> findByYear(Integer year);
	
	Optional<Agm> findByYearAndIsDeleted(Integer year, boolean b );

	Page<Agm> findByisDeleted(boolean b, Pageable pageable);

}
