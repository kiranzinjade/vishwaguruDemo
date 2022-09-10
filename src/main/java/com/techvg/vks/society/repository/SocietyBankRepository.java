package com.techvg.vks.society.repository;

import com.techvg.vks.society.domain.SocietyBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocietyBankRepository extends JpaRepository<SocietyBank, Long> {

	Optional<SocietyBank> findByBankName(String name);

	Optional<SocietyBank> findByIfsccode(String ifsccode);

	Boolean existsByIfsccode(String ifsccode);

	Page<SocietyBank> findByStatus(String status, Pageable pageable);
	
	Page<SocietyBank> findByisDeleted(Pageable pageable,boolean deleted);


	List<SocietyBank> findByStatus(String status);

	SocietyBank findByAccHeadCodeAndIsDeleted(String accHead,boolean flag);

	List<SocietyBank> findAll();
	Optional<SocietyBank> findByIfsccodeAndIsDeleted(String ifsccode,boolean deleted);

	Optional<SocietyBank> findByBankNameAndIsDeleted(String name,boolean deleted);


	Optional<SocietyBank> findByBranchName(String branchName);
	Optional<SocietyBank> findByAccHeadCode(String accHeadCode);
	

	SocietyBank findByAccountNumberAndIsDeleted(Long accountNo, boolean flag);

}
