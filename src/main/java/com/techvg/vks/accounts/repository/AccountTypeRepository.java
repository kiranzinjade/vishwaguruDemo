package com.techvg.vks.accounts.repository;

import com.techvg.vks.accounts.domain.AccountType;

import com.techvg.vks.society.domain.SocietyBank;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

	Optional<AccountType>   findByNameAndIsDeleted(String name, boolean b);
	Optional<AccountType>   findByName(String name);
    
    List<AccountType> findByisDeleted( boolean b);
}
