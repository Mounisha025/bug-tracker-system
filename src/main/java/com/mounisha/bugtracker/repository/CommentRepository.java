package com.mounisha.bugtracker.repository;

import com.mounisha.bugtracker.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository
        extends JpaRepository<Comment, Long> {

    List<Comment> findByBugId(
            Long bugId
    );
}