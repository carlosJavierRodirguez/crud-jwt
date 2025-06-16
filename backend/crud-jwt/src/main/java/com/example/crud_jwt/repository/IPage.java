package com.example.crud_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud_jwt.model.Page;

public interface IPage extends JpaRepository<Page, Integer> {

}
