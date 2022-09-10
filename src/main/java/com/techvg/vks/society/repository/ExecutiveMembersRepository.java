package com.techvg.vks.society.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techvg.vks.society.domain.ExecutiveMember;

public interface ExecutiveMembersRepository extends JpaRepository<ExecutiveMember, Long> {

	public Page<ExecutiveMember> findByElectedFrom(Date electedFrom, Pageable page);

	public Page<ExecutiveMember> findByDesignationIn(List<String> designations, Pageable page);

	public Page<ExecutiveMember> findByElectedFromAndDesignationIn(Date electedFrom, List<String> designations,
			Pageable page);

	Optional<ExecutiveMember> findByMemberId(Long memberId);

	public Page<ExecutiveMember> findByisDeleted(Pageable page, boolean b);
}
