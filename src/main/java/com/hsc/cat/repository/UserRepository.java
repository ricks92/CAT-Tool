package com.hsc.cat.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hsc.cat.entity.UserDetails;
@Repository
public interface UserRepository extends JpaRepository<UserDetails, String>{

	
	@Modifying
	@Transactional
	@Query("update UserDetails ud set ud.password=:password where ud.username=:username")
	int updatePasswordInDB(@Param("username") String username ,@Param("password") String password);
	
}
