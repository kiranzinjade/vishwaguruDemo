package com.techvg.vks.membership.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techvg.vks.membership.domain.LandDocument;


@Repository
public interface LandDocumentRepository extends JpaRepository<LandDocument,Long> {
	

}
