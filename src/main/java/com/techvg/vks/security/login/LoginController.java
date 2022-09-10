package com.techvg.vks.security.login;

import com.techvg.vks.security.jwt.JwtProvider;
import com.techvg.vks.security.jwt.JwtResponse;
import com.techvg.vks.security.jwt.LoginForm;
import com.techvg.vks.security.jwt.ResponseMessage;
import com.techvg.vks.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class LoginController {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
		String jwt = null;
		UserDetails userDetails = null;
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			jwt = jwtProvider.generateJwtToken(authentication);
			userDetails = (UserDetails) authentication.getPrincipal();
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage("Please enter valid username and password!"),
					HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	}

}