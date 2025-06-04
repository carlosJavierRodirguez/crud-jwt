package com.example.crud_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud_jwt.model.PermissionRole;

public interface IPermissionRole extends JpaRepository<PermissionRole, Integer> {

}
