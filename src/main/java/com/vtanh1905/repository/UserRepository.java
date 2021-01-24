package com.vtanh1905.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtanh1905.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByEmail(String email);
}
