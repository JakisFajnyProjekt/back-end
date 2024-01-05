package com.pl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:messages.properties")
public class MessagePropertiesConfig {

    @Value("${authentication.invalidCredentials.password}")
    private String invalidPassword;
    @Value("${authentication.invalidCredentials.email}")
    private String invalidEmail;
    @Value("${authentication.emailTaken}")
    private String emailTaken;
    @Value("${authentication.invalidCredentials}")
    private String invalidCredentials;

    public String getInvalidCredentials() {
        return invalidCredentials;
    }

    public String getInvalidPassword() {
        return invalidPassword;
    }

    public String getInvalidEmail() {
        return invalidEmail;
    }

    public String getEmailTaken() {
        return emailTaken;
    }
}
