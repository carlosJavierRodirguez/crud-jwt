package com.example.crud_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud_jwt.model.RecoveryRequest;

public interface IRecoveryRequest extends JpaRepository<RecoveryRequest, Integer> {

}
