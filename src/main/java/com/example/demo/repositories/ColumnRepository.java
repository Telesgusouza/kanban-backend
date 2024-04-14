package com.example.demo.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Column;

public interface ColumnRepository extends JpaRepository<Column, UUID> {

}
