package com.storediscounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.storediscounts.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("select u from User u where u.userName=?1")
	public User findUserByUserName(String username);

}
