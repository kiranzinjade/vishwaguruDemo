package com.techvg.vks.society.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techvg.vks.society.domain.CropRegistration;

@Repository
public interface CropRegistrationRepository extends JpaRepository<CropRegistration, Long>{

	Page<CropRegistration> findByisDeleted(Pageable pageable,boolean deleted);
	
	Optional<CropRegistration> findByCropNameAndYearAndSeasonAndIsDeleted(String name,Integer year,String season,boolean deleted);

	List<CropRegistration> findByisDeleted(boolean b);
	
	@Query(value="select c FROM CropRegistration c WHERE c.season=:season AND c.isDeleted=:b")
	List<CropRegistration> findBySeasonAndIsDeleted(@Param("season")String season,boolean b);

}
