package com.techvg.vks.membership.repository;

import com.techvg.vks.membership.domain.MemCropReg;
import com.techvg.vks.trading.domain.PurchaseOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MemCropRegRepository extends JpaRepository<MemCropReg, Long> {

   // Optional<MemCropReg> findByCrop_CropNameAndMember_IdAndSeasonAndYear(String cropName, Long memberId, String season, int year);

    List<MemCropReg> findByMember_IdAndYear(Long memberId, int year);

    List<MemCropReg> findByYear(int year);

    Page<MemCropReg> findByYear(int year, Pageable pageable);
    
    @Query(value="SELECT m FROM MemCropReg m WHERE m.member.id=:memberId AND m.year=YEAR(CURRENT_DATE())")
    List<MemCropReg>findCropsByCurrentYear(@Param("memberId")Long memberId);
    
    @Query(value="SELECT m FROM MemCropReg m WHERE m.year=YEAR(CURRENT_DATE())")
    List<MemCropReg>findByCurrentYear();
    
    @Query(value="select s from MemCropReg s where s.crop.cropName=:cropName and s.member.id=:memberId and s.season=:season and s.year=:year")
    Optional<MemCropReg>findByCropNameAndMemberIdAndSeason(@Param("cropName")String cropName,@Param("memberId")Long memberId,@Param("season")String season,@Param("year")int year);

	List<MemCropReg> findBykmpStatus(boolean b);

	@Query(value="SELECT Distinct member_id FROM MemCropReg",nativeQuery = true)
	List<Long> findAllMember();
	

}
