package com.techvg.vks.share.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techvg.vks.share.domain.ShareHistory;
@Repository
public interface ShareHistoryRepository extends JpaRepository<ShareHistory,Long> {

}
