package com.techvg.vks.society.repository;

import com.techvg.vks.society.domain.NpaSetting;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NpaSettingRepository extends JpaRepository<NpaSetting, Long>{
	Page<NpaSetting> findByisDeleted(Pageable pageable,boolean deleted);

	List<NpaSetting> findByisDeleted(boolean b);
   
    Optional<NpaSetting> findByClassificationAndIsDeleted(String classification,boolean deleted);
    
    Optional<NpaSetting> findByClassificationAndDurationStartAndDurationEndAndIsDeleted(String classification,Integer durationStart,Integer durationEnd,boolean deleted);
    

}
