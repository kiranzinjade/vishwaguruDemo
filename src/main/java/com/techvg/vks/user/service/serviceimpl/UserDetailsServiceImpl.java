package com.techvg.vks.user.service.serviceimpl;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.security.exception.UserNotFoundException;
import com.techvg.vks.user.bo.User;
import com.techvg.vks.user.bo.UserPrinciple;
import com.techvg.vks.user.mapper.UserMapper;
import com.techvg.vks.user.model.ChangePwd;
import com.techvg.vks.user.model.UserDto;
import com.techvg.vks.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	private final UserMapper userMapper;
	private final PasswordEncoder encoder;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
		
		return UserPrinciple.build(user);
	}
   
    public User getUser(Authentication authentication) {
		
		UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
		Optional<User> user = userRepository.findById(userPrincipal.getId());
				if (!user.isPresent()) {
			throw new UserNotFoundException("User can not be found with id: " + userPrincipal.getId());
		}
		
		return user.get();
	}

	
	public Optional<String> getRoles(Authentication authentication) {
		
		UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
		
		Set<String> payrollAuthorities = AuthorityUtils.authorityListToSet(userPrincipal.getAuthorities());
		
		return payrollAuthorities.stream().findFirst();
	}
	
    
    public Page<User> findAll(Pageable pageable)
        {
	    logger.debug("Request to get User : {}"); 
         return userRepository.findAllByIsDeleted(true,pageable);
	
        }

	public UserDto changePassword(ChangePwd changePwd){
		User user = userRepository.findByUsername(changePwd.getUserName())
				.orElseThrow(() -> new NotFoundException("No User found for username : " +changePwd.getUserName()));
		user.setPassword(encoder.encode(changePwd.getNewPassword()));
		user = userRepository.save(user);
		return userMapper.toDto(user);
	}

}