package com.techvg.vks.membership.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techvg.vks.membership.domain.Land;
import com.techvg.vks.membership.domain.MemberBelonging;

@Repository
public interface MemberBelongingRepository extends JpaRepository<MemberBelonging,Long> {

	@Query(value= "select b from MemberBelonging b where b.member.id=:memberId and b.belongingType=:belongingType")
	MemberBelonging findByBelongingType(@Param("belongingType")String belongingType,@Param("memberId")Long memberId);

	Page<MemberBelonging> findByisDeleted(boolean b, Pageable pageable);

	@Query(value="select b from MemberBelonging b where b.member.id=:memberId ")
	List<MemberBelonging> findByMemberId(@Param("memberId") Long memberId);
}
