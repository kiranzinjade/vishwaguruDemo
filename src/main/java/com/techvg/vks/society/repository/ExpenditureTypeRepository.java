package com.techvg.vks.society.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techvg.vks.society.domain.ExpenditureType;

@Repository
public interface ExpenditureTypeRepository extends JpaRepository<ExpenditureType,Long> {

	Page< ExpenditureType> findByisDeleted(Pageable pageable, boolean b);

	Optional<ExpenditureType> findByExpenditureTypeAndIsDeleted(String expenditureType, boolean b);

}

