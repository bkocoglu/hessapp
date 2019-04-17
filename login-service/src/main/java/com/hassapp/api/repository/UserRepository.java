package com.hassapp.api.repository;

import com.hassapp.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String>, Repository<User,String> {
    List<User> findByMail(String mail);

    List<User> findUserByMailAndPassword(String mail, String password);
}
