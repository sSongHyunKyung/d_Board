package com.devBoard.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.devBoard.domain.BoardContents;
import com.devBoard.domain.FormContent;
import com.devBoard.service.ContentService;
import com.devBoard.service.ListService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ContentController {
	
	private final ContentService contentService;
	private final ListService listService;
		
	// 書き込み作成
	@GetMapping("/contentCreate") 
		public String contentCreate(FormContent formContent) {
		return "content-form";
	}
	
	
	// 書き込み作成
	@PostMapping("/contentCreate")
	public String contentCreate(@Valid @ModelAttribute("formContent") FormContent formContent, 
			BindingResult bindingResult, @RequestParam("imageFile") MultipartFile imageFile) {
		
		if (bindingResult.hasErrors()) {
			return "content-form";
		}
		
		this.contentService.contentCreate(formContent, imageFile);
		return "redirect:/list";
	}
	
	// 書き込み修正
    @GetMapping("/contentUpdate/{id}")
    public String contentModify(@PathVariable("id") Integer id, Model model) {
    	
    		BoardContents boardContents= this.listService.getContent(id);
    		model.addAttribute("formContent", boardContents);
    		
    		return "board-update";
    }
    
    // 書き込み修正
    @PostMapping("/contentUpdate/{id}")
    public String contentModify(@Valid  @ModelAttribute("formContent") FormContent formcontent,
    		BindingResult bindingResult,
    		@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
    		Model model,
    		@PathVariable("id") Integer id){
    	
        BoardContents boardContents = this.listService.getContent(id);
        
        // パスワード入力なし
        if (formcontent.getContentPasswd() == null) {
            return "passwd-alert"; 
        }
        
        // 違うパスワード
        if (!boardContents.getContentPasswd().equals(formcontent.getContentPasswd())) {
            return "passwd-alert";
        }
 
        if (bindingResult.hasErrors()) {
            return "board-update";
        }
        
        
        // イメージ処理
        if (imageFile != null && !imageFile.isEmpty()) {
            this.contentService.updateImage(boardContents, imageFile);
        }
        
        this.contentService.modify(boardContents, formcontent);
        
        return String.format("redirect:/board-detail/%s", id);
    	}
    
    
    
    @GetMapping("/list")
    public String list(Model model, 
    		@RequestParam(value="page", defaultValue="0") int page,
    		@RequestParam(name = "category", required = false, defaultValue = "") String category,
    		@RequestParam(value = "kw", defaultValue = "") String kw) {
    	
        Page<BoardContents> paging = this.contentService.getList(page, kw, category);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("category", category);
        return "board-list";
    }
    
}
    
