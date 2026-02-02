package com.microservices.login.loginRepository.LoginRepo;

import com.microservices.login.loginModel.LoginModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepo extends JpaRepository<LoginModel, Long> {
    boolean existsByEmailId(String s);

    boolean existsByPassword(String password);

    Optional<LoginModel> findByEmailId(String s);
}
