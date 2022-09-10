package com.techvg.vks.society.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.society.domain.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long>{

	Page<Notification> findByisDeleted(Pageable pageable, boolean b);

	Optional<Notification> findByNotificationType(String maturityNotification);

	

	

}
