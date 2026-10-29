package com.mounisha.bugtracker.service;

import com.mounisha.bugtracker.entity.Bug;
import com.mounisha.bugtracker.entity.Comment;
import com.mounisha.bugtracker.entity.User;

import com.mounisha.bugtracker.repository.BugRepository;
import com.mounisha.bugtracker.repository.CommentRepository;
import com.mounisha.bugtracker.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final BugRepository bugRepository;

    private final UserRepository userRepository;

    public CommentService(
            CommentRepository commentRepository,
            BugRepository bugRepository,
            UserRepository userRepository
    ) {

        this.commentRepository = commentRepository;
        this.bugRepository = bugRepository;
        this.userRepository = userRepository;
    }

    public Comment addComment(
            Comment comment
    ) {

        Bug bug =
                bugRepository.findById(
                                comment.getBug().getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Bug not found"
                                )
                        );

        System.out.println("COMMENT API HIT");

        System.out.println(
                "Comment User = " +
                        comment.getUser()
        );

        System.out.println(
                "User Id = " +
                        comment.getUser().getId()
        );

        System.out.println(
                "Bug Id = " +
                        comment.getBug().getId()
        );

        User user =
                userRepository.findById(
                                comment.getUser().getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        comment.setBug(bug);

        comment.setUser(user);

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByBug(
            Long bugId
    ) {

        return commentRepository.findByBugId(
                bugId
        );
    }
}