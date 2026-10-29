package com.mounisha.bugtracker.controller;

import com.mounisha.bugtracker.entity.Comment;
import com.mounisha.bugtracker.service.CommentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService
            commentService;

    public CommentController(
            CommentService commentService
    ) {
        this.commentService =
                commentService;
    }

    @PostMapping
    public Comment addComment(
            @RequestBody Comment comment
    ) {

        System.out.println(
                "COMMENT API HIT"
        );

        return commentService
                .addComment(comment);
    }

    @GetMapping("/bug/{bugId}")
    public List<Comment> getComments(
            @PathVariable Long bugId
    ) {
        return commentService
                .getCommentsByBug(
                        bugId
                );

    }


}