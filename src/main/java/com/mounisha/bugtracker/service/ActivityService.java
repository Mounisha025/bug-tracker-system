package com.mounisha.bugtracker.service;

import com.mounisha.bugtracker.entity.Activity;
import com.mounisha.bugtracker.entity.Bug;
import com.mounisha.bugtracker.entity.User;
import com.mounisha.bugtracker.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(
            ActivityRepository activityRepository
    ) {

        this.activityRepository = activityRepository;

    }

    public Activity createActivity(

            String action,

            String description,

            Bug bug,

            User user

    ) {

        Activity activity = new Activity();

        activity.setAction(action);

        activity.setDescription(description);

        activity.setBug(bug);

        activity.setUser(user);

        return activityRepository.save(activity);

    }

    public List<Activity> getActivitiesByBug(
            Bug bug
    ) {

        return activityRepository.findByBugOrderByCreatedAtDesc(bug);

    }

}