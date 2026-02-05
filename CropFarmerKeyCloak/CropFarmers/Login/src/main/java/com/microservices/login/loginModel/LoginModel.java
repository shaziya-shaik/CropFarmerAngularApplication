package com.microservices.login.loginModel;

import com.microservices.login.loginEnum.loginRole.LoginRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "login")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginModel {
    @Id
    private String emailId;
    private String password;
    private LoginRole Role;


}
