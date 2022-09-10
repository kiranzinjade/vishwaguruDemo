package com.techvg.vks.society.repository;

import com.techvg.vks.society.domain.DepositType;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositTypeRepository extends JpaRepository<DepositType, Long> {
	
	Page<DepositType> findByisDeleted(Pageable pageable,boolean deleted);

	Optional<DepositType> findByAccountTypeAndIsDeleted(String accountType, boolean b);

	List<DepositType> findByisDeleted(boolean b);
}
