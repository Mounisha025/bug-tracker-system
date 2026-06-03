package com.mounisha.bugtracker.service;

import com.mounisha.bugtracker.entity.Bug;
import com.mounisha.bugtracker.enums.BugStatus;
import com.mounisha.bugtracker.repository.BugRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BugService {

    private final BugRepository bugRepository;

    public BugService(BugRepository bugRepository) {
        this.bugRepository = bugRepository;
    }

    public Bug createBug(Bug bug) {
        return bugRepository.save(bug);
    }

    public List<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    public Optional<Bug> getBugById(Long id) {
        return bugRepository.findById(id);
    }

    public List<Bug> getBugsByStatus(BugStatus status) {
        return bugRepository.findByStatus(status);
    }

    public void deleteBug(Long id) {
        bugRepository.deleteById(id);
    }
}