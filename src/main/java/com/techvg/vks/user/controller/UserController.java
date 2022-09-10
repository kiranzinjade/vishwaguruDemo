
package com.techvg.vks.user.controller;


import com.techvg.vks.security.role.Role;
import com.techvg.vks.security.role.RoleRepository;
import com.techvg.vks.user.bo.User;
import com.techvg.vks.user.model.ChangePwd;
import com.techvg.vks.user.model.UserDto;
import com.techvg.vks.user.repository.UserRepository;
import com.techvg.vks.user.service.serviceimpl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/secure/users")
public class UserController {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final UserDetailsServiceImpl userDetailsService;

	
	@GetMapping("/getallusers")
	public Page<User> findAll(Pageable pagebale){
		return userDetailsService.findAll(pagebale);
		
	}
	

	@GetMapping("/{username}")
	public Optional<User> findUserByUsername(@PathVariable("username") String username ){
		return userRepository.findUserByUsername(username);
		
	}
	
	
	@GetMapping("/getRoleList")
	public List<Role> getRoleNames(){
		return roleRepository.findAll();
		
	}

	@PostMapping("/chngpwd")
	public ResponseEntity<UserDto> changePassword(@RequestBody ChangePwd changePwd) {
		log.debug("REST request to save loan Demand details : {}", changePwd);
		System.out.println(" change pwd "+ changePwd);
		UserDto dto = userDetailsService.changePassword(changePwd);
		return new ResponseEntity<>( dto, HttpStatus.CREATED);
	}

}

