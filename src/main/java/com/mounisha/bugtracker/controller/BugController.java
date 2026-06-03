package com.mounisha.bugtracker.controller;

import com.mounisha.bugtracker.entity.Bug;
import com.mounisha.bugtracker.enums.BugStatus;
import com.mounisha.bugtracker.service.BugService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bugs")
public class BugController {

    private final BugService bugService;

    public BugController(BugService bugService) {
        this.bugService = bugService;
    }

    @PostMapping
    public Bug createBug(@RequestBody Bug bug) {
        return bugService.createBug(bug);
    }

    @GetMapping
    public List<Bug> getAllBugs() {
        return bugService.getAllBugs();
    }

    @GetMapping("/{id}")
    public Optional<Bug> getBugById(@PathVariable Long id) {
        return bugService.getBugById(id);
    }

    @GetMapping("/status/{status}")
    public List<Bug> getBugsByStatus(@PathVariable BugStatus status) {
        return bugService.getBugsByStatus(status);
    }

    @DeleteMapping("/{id}")
    public String deleteBug(@PathVariable Long id) {
        bugService.deleteBug(id);
        return "Bug deleted successfully";
    }
}