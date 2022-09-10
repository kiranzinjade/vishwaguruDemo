package com.techvg.vks.society.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techvg.vks.society.domain.Parameter;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long>{
	Optional<Parameter> findByValueAndTypeAndIsDeleted (String value,String type, boolean b);
	
	Page<Parameter> findByisDeleted(Pageable pageable,boolean deleted);
	
	List<Parameter> findByisDeleted(boolean deleted);

	List<Parameter> findByTypeAndIsDeleted(String type, boolean b);
}
