

package com.techvg.vks.user.repository;

import com.techvg.vks.user.bo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Page<User> findAllByIsDeleted(boolean b, Pageable pageable);

	@Query(value = "SELECT * FROM users  WHERE users.username=:username", nativeQuery = true)
	Optional<User> findUserByUsername(@Param("username") String username);


}
	

