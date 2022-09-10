package com.techvg.vks.society.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.techvg.vks.society.domain.Depreciation;

@Repository
public interface DepreciationRepository extends JpaRepository<Depreciation, Long>{


}
