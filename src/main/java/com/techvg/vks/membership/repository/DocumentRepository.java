package com.techvg.vks.membership.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techvg.vks.membership.domain.Document;


public interface DocumentRepository extends JpaRepository<Document, Long> {

	Page<Document> findByisDeleted(boolean status, Pageable pageable);

	Document findByMember_Id(long memberId);


}
