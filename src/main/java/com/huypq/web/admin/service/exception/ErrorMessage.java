package com.huypq.web.admin.service.exception;

public class ErrorMessage {
    private ErrorMessage(){}

    public static final String USER_NOT_FOUND = "user_not_found";
    public static final String INVALID_OTP = "otp_is_not_valid";
    public static final String DOMAIN_NOT_FOUND = "domain_not_found";
    public static final String EMAIL_NOT_FOUND = "email_not_found";
    public static final String INVALID_DOMAIN = "invalid_domain";
    public static final String OTP_EXPIRED = "otp_is_expired";
    public static final String INVALID_PASSWORD = "password_is_invalid";
    public static final String INVALID_OLD_PASSWORD = "old_password_is_invalid";
    public static final String INVALID_NEW_PASSWORD = "new_password_is_invalid";
    public static final String PASSWORD_DO_NOT_MATCH = "password_do_not_match";
    public static final String INVALID_USERNAME_OR_PASSWORD = "email_or_password_invalid";
    public static final String ACCOUNT_IS_LOCKED = "account_is_locked";
    public static final String ACCOUNT_IS_DISABLED = "account_is_disabled";
}
