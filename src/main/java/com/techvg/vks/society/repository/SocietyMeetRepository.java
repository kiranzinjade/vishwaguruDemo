package com.techvg.vks.society.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techvg.vks.society.domain.SocietyMeeting;

@Repository
public interface SocietyMeetRepository extends JpaRepository<SocietyMeeting, Long> {

	Optional<SocietyMeeting> findByMeetingNo(String meetingNo);

	Page<SocietyMeeting> findByIsDeleted(boolean b, Pageable pageable);

}
