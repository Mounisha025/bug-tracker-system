package com.mounisha.bugtracker.repository;

import com.mounisha.bugtracker.entity.Activity;
import com.mounisha.bugtracker.entity.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository
        extends JpaRepository<Activity, Long> {

    List<Activity> findByBugOrderByCreatedAtDesc(Bug bug);

}