package com.chernytskyi.university.wad.repository;

import com.chernytskyi.university.wad.domain.RolesEnum;
import com.chernytskyi.university.wad.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(RolesEnum role);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
