package dev.hyuk.blogging.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import dev.hyuk.blogging.domain.User;
import dev.hyuk.blogging.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
// 스프링 시큐리티에서 사용자 정보를 가져오는 인터페이스
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    // 사용자 이름(email)으로 사용자의 정보를 가져오는 메서드 
    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
          .orElseThrow(() -> new IllegalArgumentException((email)));
    }
}
