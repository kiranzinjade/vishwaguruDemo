package com.techvg.vks.society.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.techvg.vks.society.domain.Prerequisites;

public interface PrerequisitesRepository extends JpaRepository<Prerequisites, Long>{
	Page<Prerequisites> findByisDeleted(Pageable pageable,boolean deleted);
	
	Optional<Prerequisites> findByDocumentNameAndIsDeleted(String name,boolean deleted);


}
