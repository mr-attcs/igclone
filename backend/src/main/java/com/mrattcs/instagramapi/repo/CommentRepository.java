package com.mrattcs.instagramapi.repo;

import com.mrattcs.instagramapi.modal.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
