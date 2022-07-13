package com.karthick.io.phones.parser.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karthick.io.phones.parser.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

}
