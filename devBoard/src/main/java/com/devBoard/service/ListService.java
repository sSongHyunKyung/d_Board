package com.devBoard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.devBoard.domain.BoardComments;
import com.devBoard.domain.BoardContents;
import com.devBoard.exception.DataNotFoundException;
import com.devBoard.repository.CommentRepository;
import com.devBoard.repository.ContentsRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ListService {

	private final ContentsRepository contentsRepository;
	private final CommentRepository commentRepository;
	
	
	public List<BoardContents> getList() {
	    Sort sort = Sort.by(Sort.Direction.DESC, "contentAt");
	    return this.contentsRepository.findAll(sort);
	}
	
	public BoardContents getContent(Integer id) {
		Optional<BoardContents> content = this.contentsRepository.findById(id);
		
		if(content.isPresent()) {
			return content.get();
		} else {
			throw new DataNotFoundException("Content Not Found");
		}
	}
	
	public BoardComments getComment(Integer id) {
		Optional<BoardComments> comment = this.commentRepository.findById(id);
		
		if(comment.isPresent()) {
			return comment.get();
		} else {
			throw new DataNotFoundException("Content Not Found");
		}
	}
	
}
