package dev.hyuk.blogging.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.hyuk.blogging.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // email 로 사용자 정보 가져오기.
}
