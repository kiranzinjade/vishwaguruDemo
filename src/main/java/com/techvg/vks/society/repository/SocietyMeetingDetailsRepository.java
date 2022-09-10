package com.techvg.vks.society.repository;

import com.techvg.vks.society.domain.SocietyMeetingDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SocietyMeetingDetailsRepository extends JpaRepository<SocietyMeetingDetails, Long> {

    Page<SocietyMeetingDetails> findByIsDeleted(boolean b, Pageable pageable);

    List<SocietyMeetingDetails> findByIsDeleted(boolean b);
    
    @Query(value="select s from SocietyMeetingDetails s where s.isDeleted=:b and s.type=:type")
    List<SocietyMeetingDetails> findByIsDeletedAndType(boolean b,@Param("type")String type);
    
    @Query(value="select s from SocietyMeetingDetails s where s.societyMeeting.id=:meetingId and s.isDeleted=:b")
    List<SocietyMeetingDetails> findByMeetingIdAndIsDeleted(@Param("meetingId") Long meetingId, boolean b);

}
