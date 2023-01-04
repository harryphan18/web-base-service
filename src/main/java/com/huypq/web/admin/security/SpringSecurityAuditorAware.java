package com.huypq.web.admin.security;

import com.huypq.web.admin.config.AppConstants;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(AppConstants.SYSTEM_ACCOUNT));
    }
}
