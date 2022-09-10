package com.techvg.vks.membership.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.model.MemberDto;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
	
	
	
	     Optional<Member> existsByAadharCard(String aadharCard);
	     
	 	 Optional<Member> findByAadharCard(String aadharCard);
	 	 
	 	Optional<Member> findByPanCard(String panCard);
	
		 Page<Member> findByStatus(String status, Pageable pageable);
		 
		 Optional<Member> findById(Long MemberId);

		 List<Member> findByStatus(String status);

		 List<Member> findAll();
		 
		 @Query(value="select * from member JOIN land_details ON member.id = land_details.member_id ",nativeQuery=true)
		 List <Member> findMemberLandAndActive();

		List<Member> findBykmpStatus(boolean b);

		Page<Member> findByisDeleted(boolean b, Pageable pageable);

		@Query(value="select * from member JOIN loan_details ON member.id = loan_details.member_id WHERE loan_details.loan_status=:status ",nativeQuery=true) 
		List<Member> findMemberByLoanStatus(@Param("status") String status);

		@Query(value="select * from member JOIN deposits ON member.id = deposits.member_id WHERE member.status='A' and deposits.deposit_status ='Open' ",nativeQuery=true)
		List<Member> findMemberByDeposits();

		@Query(value="select count(*) from member where status='A' and is_deleted=false",nativeQuery=true)
		int findMemberByStatus();
	
}
