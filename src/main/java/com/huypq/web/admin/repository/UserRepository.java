package com.huypq.web.admin.repository;

import com.huypq.web.admin.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByUsernameIgnoreCase(String login);

    @EntityGraph(attributePaths = "authority")
    Optional<User> findOneWithAuthorityByEmailIgnoreCase(String email);

}
