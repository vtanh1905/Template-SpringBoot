package com.vtanh1905.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vtanh1905.entity.User;
import com.vtanh1905.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private UserRepository userRepository;
	
	private AuthenticationManager authenticationManager;
	
	public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
	}
	
	@PostMapping("register")
	public Object register(@RequestParam String email, @RequestParam String password, @RequestParam int role_id) {
		try {
			userRepository.save(new User(email, BCrypt.hashpw(password, BCrypt.gensalt()), role_id));
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("login")
	public Object login(@RequestParam String email, @RequestParam String password) {
		
		// Security support us check email and password
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// Tạo chuỗi Token
		Date now = new Date();
		String token = Jwts.builder()
			.setSubject(email)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + 864000000L))
			.signWith(SignatureAlgorithm.HS512, "secretKey")
			.compact();
		
		return new ResponseEntity<>(token, HttpStatus.OK);
	}
}
