package com.elevatemart.security.elevatemartsecurity.repository;

import com.elevatemart.security.elevatemartsecurity.domain.ElevateMartUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElevateMartUserRepository extends JpaRepository<ElevateMartUser,Integer> {
     Optional<ElevateMartUser> findByEmail(String email);
}
