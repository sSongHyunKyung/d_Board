package com.devBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.devBoard.domain.BoardContents;
import com.devBoard.domain.FormComment;
import com.devBoard.service.CommentService;
import com.devBoard.service.ListService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommentController {

	private final ListService listService;
	private final CommentService commentService;

	@PostMapping("/create/{id}")
	public String createComment(Model model, @PathVariable("id") Integer id,
			@ModelAttribute @Valid FormComment formComment, BindingResult bindingResult ) {
        BoardContents boardContents = this.listService.getContent(id);
        
        
        if (bindingResult.hasErrors()) {
        	model.addAttribute("content", boardContents);
        	return "board-detail";
        }
        
        this.commentService.create(boardContents, formComment);
        

        System.out.println(id);
		return String.format("redirect:/board-detail/%s", id);
	}
}
