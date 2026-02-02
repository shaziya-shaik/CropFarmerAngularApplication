package com.microservices.login.loginDTO;



import com.microservices.login.loginEnum.loginRole.LoginRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


public record LoginRequestDTO( String emailId,
        String password, @Enumerated(EnumType.STRING) LoginRole role) {



}
