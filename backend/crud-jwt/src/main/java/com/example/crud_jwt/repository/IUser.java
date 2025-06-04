package com.example.crud_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crud_jwt.model.User;

public interface IUser extends JpaRepository<User, Integer> {

}
