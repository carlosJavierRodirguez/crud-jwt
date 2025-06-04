package com.example.crud_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud_jwt.model.Role;

public interface IRole extends JpaRepository<Role, Integer> {

}
