package com.mounisha.bugtracker.repository;

import com.mounisha.bugtracker.entity.Bug;
import com.mounisha.bugtracker.enums.BugStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BugRepository
        extends JpaRepository<Bug, Long> {

    List<Bug> findByStatus(BugStatus status);
}