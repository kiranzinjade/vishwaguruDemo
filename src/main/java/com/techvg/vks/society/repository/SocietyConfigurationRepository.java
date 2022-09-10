package com.techvg.vks.society.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techvg.vks.society.domain.SocietyConfiguration;

public interface SocietyConfigurationRepository extends JpaRepository<SocietyConfiguration, Long> {

	Optional<SocietyConfiguration> findByConfigName(String configName);
	List<SocietyConfiguration> findByConfigType(String configType);
	Page<SocietyConfiguration> findByStatus(String status, Pageable pageable);
	
	
	}
