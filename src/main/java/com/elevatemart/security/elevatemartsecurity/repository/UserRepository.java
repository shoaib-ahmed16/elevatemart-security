package com.elevatemart.security.elevatemartsecurity.repository;

import com.elevatemart.security.elevatemartsecurity.domain.RegisterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<RegisterUser,Integer> {

     Optional<RegisterUser> findByEmail(String email);
}
