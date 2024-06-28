package com.devBoard.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.devBoard.domain.BoardContents;
import com.devBoard.domain.BoardImages;
import com.devBoard.domain.FormComment;
import com.devBoard.repository.ImagesRepository;
import com.devBoard.service.ListService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ListController {

	private final ListService listService;
	private final ImagesRepository imagesRepository;
	
	//List
	@GetMapping("/")
	public String index(Model model) {
		
		List<BoardContents> contentList = this.listService.getList();
		model.addAttribute("contentList", contentList);
		return "board-list";
	}
	
	
	//詳細
	@GetMapping(value = "/board-detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, FormComment formComment) {
		
		BoardContents boardContents = this.listService.getContent(id);
		
		List<BoardImages> boardImages = this.imagesRepository.findByBoardContentsContentId(boardContents.getContentId());
	
		model.addAttribute("content", boardContents);
		model.addAttribute("formComment", formComment);
		model.addAttribute("boardImages", boardImages);
		
		return "board-detail";
	}
		
}
