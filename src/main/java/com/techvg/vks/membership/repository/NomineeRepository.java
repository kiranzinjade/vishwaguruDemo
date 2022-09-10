package com.techvg.vks.membership.repository;

import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.domain.Nominee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NomineeRepository extends JpaRepository<Nominee, Long>{

	  List<Nominee> findByMember(Member member);
       
	Page<Nominee> findByIsDeleted(boolean status, Pageable pageable);
	
	List<Nominee> findByMemberId(Long memberId);

}
