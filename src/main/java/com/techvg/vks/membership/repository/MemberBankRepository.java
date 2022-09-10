package com.techvg.vks.membership.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techvg.vks.membership.domain.House;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.domain.MemberBank;
@Repository
public interface MemberBankRepository extends JpaRepository<MemberBank, Long> {

	Optional<MemberBank> findByAccountNumber(Long accountNumber);


	Page<MemberBank> findByisDeleted(boolean b, Pageable pageable);


	Optional<MemberBank> findByMember(Member member);

		

}
