package com.devBoard.domain;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormComment {

	@NotEmpty(message = "登録者を入力してください。")
	private String commentUser;
	
	@Length(min = 8, max = 50)
	@NotEmpty(message = "パスワードを入力してください。")
	private String commentPasswd;
	
	@Size(max = 1000)
	@NotEmpty(message = "コメント内容を入力してください。")
	private String commentContents;
	
	
}
