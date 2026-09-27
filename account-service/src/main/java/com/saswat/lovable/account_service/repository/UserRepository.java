package com.saswat.lovable.account_service.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.saswat.lovable.account_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameIgnoreCase(String email);
}
