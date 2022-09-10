package com.techvg.vks.accounts.repository;

import com.techvg.vks.accounts.domain.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JournalRepository extends JpaRepository<Journal, Long> {

    Optional<Journal> findByName(String name);
}
