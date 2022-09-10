package com.techvg.vks.society.repository;

import com.techvg.vks.society.domain.Agm;
import com.techvg.vks.society.domain.AgmAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

public interface AgmAttendanceRepository extends JpaRepository<AgmAttendance, Long> {


	AgmAttendance findByAgm(Agm agm);
	//@Query(value="SELECT DISTINCT AgmAttendance.agm_id FROM AgmAttendance ,Agm WHERE Agm.id=AgmAttendance.agm_id AND AgmAttendance.attendanceStatus='PRESENT' AND Agm.year>=YEAR(CURDATE())-5")
	
	@Query(value="SELECT DISTINCT  society_agm_attendance.member_id1 FROM society_agm_attendance ,society_agm_details WHERE society_agm_details.id=society_agm_attendance.agm_id AND society_agm_attendance.attendance_status='PRESENT' AND society_agm_details.year>=YEAR(CURDATE())-5",nativeQuery = true)
	Long[] findByAttendanceList();

	Page<AgmAttendance> findByisDeleted(boolean b, Pageable pageable); 
	
	Page<AgmAttendance> findByIsDeletedAndAgmId(boolean b, Pageable pageable,long agmId);
	
	@Transactional
	@Modifying
    @Query(value="UPDATE AgmAttendance s SET s.isDeleted=:b WHERE s.agm.id=:agmId")
    void setStatus(boolean b, long agmId);
		
}
