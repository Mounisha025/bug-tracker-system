package com.mounisha.bugtracker.service;

import com.mounisha.bugtracker.dto.DashboardStatsDTO;
import com.mounisha.bugtracker.enums.BugStatus;
import com.mounisha.bugtracker.repository.BugRepository;
import com.mounisha.bugtracker.repository.NotificationRepository;
import com.mounisha.bugtracker.repository.ProjectRepository;
import com.mounisha.bugtracker.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final BugRepository bugRepository;

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    private final NotificationRepository notificationRepository;

    public DashboardService(
            BugRepository bugRepository,
            UserRepository userRepository,
            ProjectRepository projectRepository,
            NotificationRepository notificationRepository
    ) {

        this.bugRepository = bugRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.notificationRepository = notificationRepository;

    }

    public DashboardStatsDTO getDashboardStats() {

        long totalBugs =
                bugRepository.count();

        long openBugs =
                bugRepository.findByStatus(BugStatus.OPEN).size();

        long inProgressBugs =
                bugRepository.findByStatus(BugStatus.IN_PROGRESS).size();

        long resolvedBugs =
                bugRepository.findByStatus(BugStatus.RESOLVED).size();

        long totalUsers =
                userRepository.count();

        long totalProjects =
                projectRepository.count();

        long totalNotifications =
                notificationRepository.count();

        return new DashboardStatsDTO(

                totalBugs,

                openBugs,

                inProgressBugs,

                resolvedBugs,

                totalUsers,

                totalProjects,

                totalNotifications

        );

    }

}