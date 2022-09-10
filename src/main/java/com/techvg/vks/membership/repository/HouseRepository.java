package com.techvg.vks.membership.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techvg.vks.membership.domain.House;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

	Optional<House> findByHouseNumber(String houseNumber);

	Page<House> findByisDeleted(boolean b, Pageable pageable);

	Optional<House> findById(Long houseId);

	Page<House> findByStatus(String string, Pageable pageable);
	
	//@Query(value="select h from House h where h.addressType='addressType' and h.member.id=:id ")
	//House findByHouseMemberId(Long id);

}
