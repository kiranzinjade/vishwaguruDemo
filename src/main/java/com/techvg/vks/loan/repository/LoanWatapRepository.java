package com.techvg.vks.loan.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techvg.vks.loan.domain.LoanWatap;
@Repository
public interface LoanWatapRepository extends JpaRepository<LoanWatap,Long> {

	Page<LoanWatap> findByisDeleted(Pageable pageable, boolean b);

	@Query(value="SELECT* FROM loan_watap WHERE slot=(SELECT MAX(slot)  FROM loan_watap)",nativeQuery = true)
	List<LoanWatap> findByLastSlot();
	
	List<LoanWatap> findBySlot(int slot);
	
	@Query(value="SELECT Distinct slot FROM loan_watap",nativeQuery = true)
	List<Integer> findByAllSlot();
	
	@Query(value="SELECT Distinct crop_id FROM loan_watap",nativeQuery = true)
	List<Long> findByAllCrop();
  
}
