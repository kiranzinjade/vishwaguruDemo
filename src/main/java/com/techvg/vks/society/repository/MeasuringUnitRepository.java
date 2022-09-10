package com.techvg.vks.society.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techvg.vks.society.domain.MeasuringUnit;

@Repository
public interface MeasuringUnitRepository extends JpaRepository<MeasuringUnit, Long> {
    Optional<MeasuringUnit> findByNameAndIsDeleted(String name,boolean b );
    
    Page<MeasuringUnit> findByisDeleted(Pageable pageable, boolean b);
    
    List<MeasuringUnit> findByisDeleted( boolean b);
}
