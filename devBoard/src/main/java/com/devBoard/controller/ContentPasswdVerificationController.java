package com.devBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devBoard.domain.BoardContents;
import com.devBoard.service.ContentService;
import com.devBoard.service.ListService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class ContentPasswdVerificationController {

	private final ListService listService;
	private final ContentService contentService;
	
    @PostMapping("/contentVerify-password")
    public String showPasswordVerificationPage(
            @RequestParam(name = "contentId") String contentId,
            @RequestParam(name = "contentPasswd") String contentPasswd,
            Model model) {
    
        model.addAttribute("contentId", contentId);
        model.addAttribute("contentPasswd", contentPasswd);
        return "contentPasswdVerification";
    }

    @PostMapping("/contentCheck-password")
    public String checkPassword(
            @RequestParam(name = "contentId") int contentId,
            @RequestParam(name = "contentPasswd") String contentPasswd,
            @RequestParam(name = "password") String password,
            Model model) {
        
    	// 削除機能
        // パスワード確認
        if (contentPasswd.equals(password)) {
        	BoardContents boardContents = this.listService.getContent(contentId);
        	this.contentService.delete(boardContents);
        	return "redirect:/list";
        } else {
            model.addAttribute("error", "パスワードが正しくありません。");
            model.addAttribute("contentId", contentId);
            model.addAttribute("contentPasswd", contentPasswd);
            return "contentPasswdVerification";
        }
    }
}
