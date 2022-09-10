package com.techvg.vks.society.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techvg.vks.society.domain.Assets;

@Repository
public interface AssetsRepository extends JpaRepository<Assets, Long>{
	
	Page<Assets> findByisDeleted(Pageable pageable,boolean deleted);

	Optional<Assets> findByAssetsNameAndIsDeleted(String assetsName,boolean deleted);

}
