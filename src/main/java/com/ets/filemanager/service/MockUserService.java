package com.ets.filemanager.service;

import com.ets.filemanager.dao.UserRepository;
import com.ets.filemanager.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class MockUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setUsername("etsadmin");
        user.setPassword(passwordEncoder.encode("etsadmin"));
        user.setEmail("admin@etstur.com.tr");
        user.setEnabled(true);

        userRepository.save(user);
    }
}
