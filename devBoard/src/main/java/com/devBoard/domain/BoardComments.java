package com.devBoard.domain;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "Board_Comments")
public class BoardComments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long commentId;

	@Column(name = "comment_at")
	private LocalDateTime commentAt;
	
    @Column(name = "comment_user", nullable = false, length = 80)
    private String commentUser;
    
    @Length(min = 8, max = 50)
    @Column(name = "comment_passwd", nullable = false, length = 50)
    private String commentPasswd;

    @Column(name = "comment_contents", nullable = false, length = 1000)
    private String commentContents;
	

	//ManyToOne Many = comment / One = BoardContent
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id", nullable = false)
	private BoardContents boardContents;
	

}
