package com.devBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devBoard.domain.BoardComments;
import com.devBoard.service.CommentService;
import com.devBoard.service.ListService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommentPasswdVerificationController {
	
	private final ListService listService;
	private final CommentService commentService;
	
    @PostMapping("/commentVerify-password")
    public String showPasswordVerificationPage(
            @RequestParam(name = "commentId") String commentId,
            @RequestParam(name = "commentPasswd") String commentPasswd,
            Model model) {
    
        model.addAttribute("commentId", commentId);
        model.addAttribute("commentPasswd", commentPasswd);
        return "commentPasswdVerification";
    }

    @PostMapping("/commentCheck-password")
    public String checkPassword(
            @RequestParam(name = "commentId") int commentId,
            @RequestParam(name = "commentPasswd") String commentPasswd,
            @RequestParam(name = "password") String password,
            Model model) {
        
    	// 削除機能
        // パスワード確認
        if (commentPasswd.equals(password)) {
        	BoardComments boardComments = this.listService.getComment(commentId);
        	this.commentService.delete(boardComments);
        	return "redirect:/list";
        } else {
            model.addAttribute("error", "パスワードが正しくありません。");
            model.addAttribute("commentId", commentId);
            model.addAttribute("commentPasswd", commentPasswd);
            return "commentPasswdVerification";
        }
    }
	
}
