package com.techvg.vks.society.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techvg.vks.society.domain.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
	
	Optional<ProductType> findByTypeAndIsDeleted (String type, boolean b);

	Page<ProductType> findByisDeleted(Pageable pageable, boolean b);
	
	List<ProductType> findByisDeleted( boolean b);

	

}
