package com.devBoard.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.devBoard.domain.BoardComments;
import com.devBoard.domain.BoardContents;
import com.devBoard.domain.FormComment;
import com.devBoard.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	
	public void create(BoardContents boardContent, FormComment formComment) {
		BoardComments comment = new BoardComments();
		comment.setCommentContents(formComment.getCommentContents());
		comment.setCommentPasswd(formComment.getCommentPasswd());
		comment.setCommentUser(formComment.getCommentUser());
		comment.setCommentAt(LocalDateTime.now());
		comment.setBoardContents(boardContent);
		this.commentRepository.save(comment);
	}
	
    public void delete(BoardComments boardComment) {
    	this.commentRepository.delete(boardComment);
    }
}
