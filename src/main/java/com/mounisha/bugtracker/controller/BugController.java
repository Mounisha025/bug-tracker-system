
package com.mounisha.bugtracker.controller;

import com.mounisha.bugtracker.entity.Bug;
import com.mounisha.bugtracker.enums.BugStatus;
import com.mounisha.bugtracker.service.BugService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/bugs")
public class BugController {

    private final BugService bugService;

    public BugController(BugService bugService) {
        this.bugService = bugService;
    }

    // CREATE BUG
    @PostMapping
    public Bug createBug(@RequestBody Bug bug) {
        return bugService.createBug(bug);
    }

    // GET ALL BUGS
    @GetMapping
    public List<Bug> getAllBugs() {
        return bugService.getAllBugs();
    }

    // GET BUG BY ID
    @GetMapping("/{id}")
    public Bug getBugById(@PathVariable Long id) {
        return bugService.getBugById(id);
    }

    // GET BUGS BY STATUS
    @GetMapping("/status/{status}")
    public List<Bug> getBugsByStatus(@PathVariable BugStatus status) {
        return bugService.getBugsByStatus(status);
    }

    // DELETE BUG
    @DeleteMapping("/{id}")
    public String deleteBug(@PathVariable Long id) {
        bugService.deleteBug(id);
        return "Bug deleted successfully";
    }

    // UPDATE BUG
    @PutMapping("/{id}")
    public Bug updateBug(
            @PathVariable Long id,
            @RequestBody Bug updatedBug
    ) {
        return bugService.updateBug(id, updatedBug);
    }

    // UPLOAD SCREENSHOT
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String uploadScreenshot(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String uploadDir = "uploads/";

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String fileName = System.currentTimeMillis()
                    + "_"
                    + (originalFilename == null
                    ? "upload"
                    : Paths.get(originalFilename).getFileName().toString());

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return fileName;

        } catch (IOException e) {
            throw new RuntimeException(
                    "File upload failed: " + e.getMessage(), e
            );
        }
    }
}
