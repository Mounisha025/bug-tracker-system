package com.mounisha.bugtracker.controller;

import com.mounisha.bugtracker.dto.DashboardStatsDTO;
import com.mounisha.bugtracker.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService
    ) {

        this.dashboardService = dashboardService;

    }

    @GetMapping
    public DashboardStatsDTO getDashboardStats() {

        return dashboardService.getDashboardStats();

    }

}