package com.techvg.vks.membership.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techvg.vks.membership.domain.NotificationDetails;

public interface NotificationDetailsRepository extends JpaRepository<NotificationDetails, Long> {

	Page<NotificationDetails> findByisDeleted(boolean b, Pageable pageable);

}
