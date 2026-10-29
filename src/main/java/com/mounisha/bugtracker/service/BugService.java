
package com.mounisha.bugtracker.service;

import com.mounisha.bugtracker.entity.Bug;
import com.mounisha.bugtracker.entity.User;
import com.mounisha.bugtracker.enums.BugStatus;
import com.mounisha.bugtracker.exception.ResourceNotFoundException;
import com.mounisha.bugtracker.repository.BugRepository;
import com.mounisha.bugtracker.repository.ProjectRepository;
import com.mounisha.bugtracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BugService {

    private final BugRepository bugRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final NotificationService notificationService;
    private final ActivityService activityService;
    private final EmailService emailService;

    public BugService(
            BugRepository bugRepository,
            UserRepository userRepository,
            ProjectRepository projectRepository,
            NotificationService notificationService,
            ActivityService activityService,
            EmailService emailService
    ) {
        this.bugRepository = bugRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.notificationService = notificationService;
        this.activityService = activityService;
        this.emailService = emailService;
    }

    // CREATE BUG
    public Bug createBug(Bug bug) {

        Bug savedBug = bugRepository.save(bug);

        // Activity
        if (savedBug.getReportedBy() != null) {
            activityService.createActivity(
                    "BUG_CREATED",
                    "Created bug: " + savedBug.getTitle(),
                    savedBug,
                    savedBug.getReportedBy()
            );
        }

        // In-app notification and assignment email
        if (savedBug.getAssignedTo() != null) {
            notificationService.createNotification(
                    "New bug assigned: " + savedBug.getTitle(),
                    savedBug.getAssignedTo()
            );

            sendAssignmentEmail(savedBug);
        } else {
            System.out.println(
                    "No assignment email sent: bug has no assignee."
            );
        }

        return savedBug;
    }

    // GET ALL BUGS
    public List<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    // GET BUG BY ID
    public Bug getBugById(Long id) {
        return bugRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bug not found with id: " + id
                        )
                );
    }

    // GET BUGS BY STATUS
    public List<Bug> getBugsByStatus(BugStatus status) {
        return bugRepository.findByStatus(status);
    }

    // DELETE BUG
    public void deleteBug(Long id) {
        if (!bugRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Bug not found with id: " + id
            );
        }

        bugRepository.deleteById(id);
    }

    // UPDATE BUG
    public Bug updateBug(Long id, Bug updatedBug) {

        Bug existingBug = bugRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bug not found with id: " + id
                        )
                );

        BugStatus oldStatus = existingBug.getStatus();
        User oldDeveloper = existingBug.getAssignedTo();

        existingBug.setTitle(updatedBug.getTitle());
        existingBug.setDescription(updatedBug.getDescription());
        existingBug.setPriority(updatedBug.getPriority());
        existingBug.setStatus(updatedBug.getStatus());
        existingBug.setAssignedTo(updatedBug.getAssignedTo());
        existingBug.setReportedBy(updatedBug.getReportedBy());
        existingBug.setProject(updatedBug.getProject());
        existingBug.setScreenshot(updatedBug.getScreenshot());

        Bug savedBug = bugRepository.save(existingBug);

        // Send assignment email only when the assignee changes
        User newDeveloper = savedBug.getAssignedTo();

        boolean assigneeChanged =
                newDeveloper != null
                        && (oldDeveloper == null
                        || !oldDeveloper.getId().equals(
                        newDeveloper.getId()
                ));

        if (assigneeChanged) {
            sendAssignmentEmail(savedBug);
        }

        // Status change activity and email
        if (oldStatus != savedBug.getStatus()) {

            activityService.createActivity(
                    "STATUS_CHANGED",
                    "Status changed from " + oldStatus
                            + " to " + savedBug.getStatus(),
                    savedBug,
                    savedBug.getReportedBy()
            );

            if (savedBug.getReportedBy() != null
                    && savedBug.getReportedBy().getId() != null) {

                userRepository.findById(
                        savedBug.getReportedBy().getId()
                ).ifPresent(user -> {

                    if (user.getEmail() != null
                            && !user.getEmail().isBlank()) {

                        try {
                            emailService.sendStatusChangedEmail(
                                    user.getEmail(),
                                    savedBug.getTitle(),
                                    oldStatus.name(),
                                    savedBug.getStatus().name()
                            );

                            System.out.println(
                                    "Status-change email accepted by mail service."
                            );

                        } catch (Exception e) {
                            System.err.println(
                                    "Status-change email failed: "
                                            + e.getMessage()
                            );
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        // Assignment activity
        if (assigneeChanged) {
            activityService.createActivity(
                    "BUG_ASSIGNED",
                    "Assigned to " + newDeveloper.getName(),
                    savedBug,
                    savedBug.getReportedBy()
            );
        }

        // In-app notification
        if (assigneeChanged) {
            notificationService.createNotification(
                    "Bug assigned: " + savedBug.getTitle(),
                    newDeveloper
            );
        }

        return savedBug;
    }

    // SHARED ASSIGNMENT EMAIL METHOD
    private void sendAssignmentEmail(Bug bug) {

        if (bug.getAssignedTo() == null
                || bug.getAssignedTo().getId() == null) {

            System.err.println(
                    "Email skipped: assignee ID is missing."
            );
            return;
        }

        Long assigneeId = bug.getAssignedTo().getId();

        // Fetch the complete user, including their email
        userRepository.findById(assigneeId).ifPresentOrElse(user -> {

            String email = user.getEmail();

            if (email == null || email.isBlank()) {
                System.err.println(
                        "Email skipped: no email address exists for user ID "
                                + assigneeId
                );
                return;
            }

            try {
                emailService.sendBugAssignedEmail(
                        email,
                        bug.getTitle(),
                        bug.getPriority().name(),
                        bug.getStatus().name()
                );

                System.out.println(
                        "Assignment email accepted by mail service for: "
                                + email
                );

            } catch (Exception e) {
                System.err.println(
                        "Assignment email failed: " + e.getMessage()
                );
                e.printStackTrace();
            }

        }, () -> System.err.println(
                "Email skipped: assignee not found. User ID: " + assigneeId
        ));
    }
}
