package com.mounisha.bugtracker.dto;

public class DashboardStatsDTO {

    private long totalBugs;

    private long openBugs;

    private long inProgressBugs;

    private long resolvedBugs;

    private long totalUsers;

    private long totalProjects;

    private long totalNotifications;

    public DashboardStatsDTO() {
    }

    public DashboardStatsDTO(
            long totalBugs,
            long openBugs,
            long inProgressBugs,
            long resolvedBugs,
            long totalUsers,
            long totalProjects,
            long totalNotifications
    ) {

        this.totalBugs = totalBugs;
        this.openBugs = openBugs;
        this.inProgressBugs = inProgressBugs;
        this.resolvedBugs = resolvedBugs;
        this.totalUsers = totalUsers;
        this.totalProjects = totalProjects;
        this.totalNotifications = totalNotifications;

    }

    public long getTotalBugs() {
        return totalBugs;
    }

    public void setTotalBugs(long totalBugs) {
        this.totalBugs = totalBugs;
    }

    public long getOpenBugs() {
        return openBugs;
    }

    public void setOpenBugs(long openBugs) {
        this.openBugs = openBugs;
    }

    public long getInProgressBugs() {
        return inProgressBugs;
    }

    public void setInProgressBugs(long inProgressBugs) {
        this.inProgressBugs = inProgressBugs;
    }

    public long getResolvedBugs() {
        return resolvedBugs;
    }

    public void setResolvedBugs(long resolvedBugs) {
        this.resolvedBugs = resolvedBugs;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalProjects() {
        return totalProjects;
    }

    public void setTotalProjects(long totalProjects) {
        this.totalProjects = totalProjects;
    }

    public long getTotalNotifications() {
        return totalNotifications;
    }

    public void setTotalNotifications(long totalNotifications) {
        this.totalNotifications = totalNotifications;
    }
}