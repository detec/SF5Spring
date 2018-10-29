package org.openbox.sf5.dao;

import java.util.Optional;

import org.openbox.sf5.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);
}
