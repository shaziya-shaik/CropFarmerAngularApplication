package com.microservices.login.loginDTO;


import com.microservices.login.loginEnum.loginRole.LoginRole;

public record LoginResponseDTO(String emailId,
                               String password,
                               LoginRole Role) {
}
