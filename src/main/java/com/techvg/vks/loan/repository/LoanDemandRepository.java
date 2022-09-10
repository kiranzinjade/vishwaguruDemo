package com.techvg.vks.loan.repository;

import com.techvg.vks.loan.domain.LoanDemand;
import com.techvg.vks.loan.model.LoanDemandCountDto;
import com.techvg.vks.society.domain.SocietyBank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LoanDemandRepository extends JpaRepository<LoanDemand, Long> {
	
	@Query(value="select l from LoanDemand l where l.crop.id IS NULL and l.isDeleted = false")
	Page<LoanDemand> findByisDeleted(Pageable pageable,boolean b);
	
	@Query(value="select l from LoanDemand l where l.isDeleted = false and l.status='Applied' and l.crop.id IS NOT NULL")
	Page<LoanDemand> findAllByCropRegistration(Pageable pageable);

	@Query(value="select l from LoanDemand l where l.crop.id IS NOT NULL and l.isDeleted = false")
	List<LoanDemand> findAllByCropRegistration();

	@Query(value="select l from LoanDemand l where  l.crop.id=:cropId and l.year=:year and l.member.id= :memberId and l.land.id= :landId")
	Optional<LoanDemand> findByMember(@Param("cropId") Long cropId, @Param("year") Integer year, @Param("memberId") Long memberId, @Param("landId") Long landId);
	
	List<LoanDemand> findAllByLoanProductProductType(String type);
	Page<LoanDemand> findByIsDeletedAndLoanProductProductType(boolean b, Pageable pageable, String type);

	@Query(value="select l from LoanDemand l where l.member.id= :memberId and l.loanProduct.id =:loanProdId and l.isDeleted = false and l.status='Applied'")
	List<LoanDemand> findByMemberIdAndLoanProductId(@Param("memberId") Long memberId, @Param("loanProdId") Long loanProdId);

	@Query(value="select l from LoanDemand l where l.member.id= :memberId and l.crop.id =:cropId and l.isDeleted = false and l.status='Generate-loanwatap'")
	List<LoanDemand> findByMemberIdAndCropId(@Param("memberId") Long memberId, @Param("cropId") Long cropId);
	
	@Query(value="select new com.techvg.vks.loan.model.LoanDemandCountDto(l.id, l.crop.cropName, l.season, l.year, count(l)) from LoanDemand l where l.isDeleted = false and l.year =:year group by l.crop.cropName")
	List<LoanDemandCountDto> findCropMemberCount(@Param("year") Integer year);

	@Query(value="select l from LoanDemand l where l.crop.id IS NOT NULL and l.isDeleted = false and l.crop.cropName=:cropName and l.year=:year")
	Page<LoanDemand> findAllByMember(Pageable pageable, @Param("cropName") String cropName, @Param("year") Integer year);

	@Query(value="select * from loan_demand join loan_product on loan_demand.loan_product_id=loan_product.id and loan_demand.is_deleted=false",nativeQuery = true)
	List<LoanDemand> findByIsDeleted();
}
