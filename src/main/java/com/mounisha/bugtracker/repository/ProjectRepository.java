package com.mounisha.bugtracker.repository;

import com.mounisha.bugtracker.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository
        extends JpaRepository<Project, Long> {
}