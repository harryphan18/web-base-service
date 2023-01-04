package com.huypq.web.admin.service;

import com.huypq.web.admin.domain.User;
import com.huypq.web.admin.dto.UserDTO;
import com.huypq.web.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUser() {
        List<User> lstUser = userRepository.findAll();
        return lstUser.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
