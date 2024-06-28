package com.devBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devBoard.domain.BoardComments;

@Repository
public interface CommentRepository extends JpaRepository<BoardComments, Integer> {}
