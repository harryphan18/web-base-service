package com.huypq.web.admin.rest;

import com.huypq.web.admin.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class AccountResource {
    private final Logger logger = LoggerFactory.getLogger(AccountResource.class);

    @GetMapping("/authenticate")
    public ResponseEntity isAuthenticated(HttpServletRequest request) {
        logger.debug("REST request to check if the current user is authenticated");
        if (SecurityUtils.isAuthenticated()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
