package com.mounisha.bugtracker.controller;

import com.mounisha.bugtracker.entity.Activity;
import com.mounisha.bugtracker.entity.Bug;
import com.mounisha.bugtracker.service.ActivityService;
import com.mounisha.bugtracker.service.BugService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;
    private final BugService bugService;

    public ActivityController(
            ActivityService activityService,
            BugService bugService) {

        this.activityService = activityService;
        this.bugService = bugService;
    }

    // GET ACTIVITIES BY BUG ID
    @GetMapping("/bug/{bugId}")
    public List<Activity> getActivitiesByBug(
            @PathVariable Long bugId) {

        // Fetch the bug. If it does not exist, the service
        // throws ResourceNotFoundException and returns HTTP 404.
        Bug bug = bugService.getBugById(bugId);

        // Return activities associated with the bug.
        return activityService.getActivitiesByBug(bug);
    }
}