package com.huypq.web.admin.rest;

import com.huypq.web.admin.dto.UserDTO;
import com.huypq.web.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserResource {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUser() {
        return ResponseEntity.ok().body(userService.getAllUser());
    }
    @GetMapping("/encode-password")
    public ResponseEntity<String> encodePassword(@RequestParam String password) {
        return ResponseEntity.ok().body(userService.encodePassword(password));
    }
}
