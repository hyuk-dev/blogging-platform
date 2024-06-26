package dev.hyuk.blogging.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.hyuk.blogging.domain.User;
import dev.hyuk.blogging.dto.AddUserRequest;
import dev.hyuk.blogging.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto){
        return userRepository.save(User.builder()
          .email(dto.getEmail())
          .password(bCryptPasswordEncoder.encode(dto.getPassword()))
          .build()).getId();
    }
}
