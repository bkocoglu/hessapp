package com.hassapp.api.repository;

import com.hassapp.api.model.AccountActivateCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountActivateCodeRepository extends JpaRepository<AccountActivateCode, Integer> {
    List<AccountActivateCode> getAccountActivateCodeByActivateCode(String activationCode);
}
