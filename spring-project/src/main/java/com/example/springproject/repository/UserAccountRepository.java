package com.example.springproject.repository;

import com.example.springproject.domain.UserAccount;
import com.example.springproject.dto.UserAccountDto;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount,String> {
}
