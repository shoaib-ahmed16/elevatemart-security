package com.elevatemart.security.elevatemartsecurity.repository;

import com.elevatemart.security.elevatemartsecurity.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Integer > {
}
