package com.devBoard.domain;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormContent {
	
	@NotEmpty(message = "タイトルを入力してください。")
	@Size(max = 255)
	private String contentTitle;
	
	@NotEmpty(message = "登録者を入力してください。")
	private String contentUser;
	
	@Length(min = 8, max = 50)
	@NotEmpty(message = "パスワードを入力してください。")
	private String contentPasswd;
	
	@Size(max = 1000)
	@NotEmpty(message = "内容を入力してください。")
	private String contentContents;	
}
