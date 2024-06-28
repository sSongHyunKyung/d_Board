package com.devBoard.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Board_Contents")
@Getter @Setter
public class BoardContents {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "content_id")
	private Long contentId;
	

	@Column(name = "content_title", nullable = false, length = 100)
	private String contentTitle;
	
	@Column(name = "content_user", nullable = false, length = 80)
	private String contentUser;
	
	@Length(min = 8, max = 50)
	@Column(name = "content_passwd", nullable = false, length = 100)
	private String contentPasswd;
	
	@Column(name = "content_contents", nullable = false, length = 1000)
	private String contentContents;

	@Column(name = "content_at")
	private LocalDateTime contentAt;
	
    @OneToMany(mappedBy = "boardContents", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardImages> images;
	
	
	@OneToMany(mappedBy = "boardContents", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BoardComments> comments;
	
    //関係管理
    public void addComment(BoardComments boardComments) {
    	comments.add(boardComments);
    	boardComments.setBoardContents(this);
    }

    public void removeComment(BoardComments boardComments) {
    	comments.remove(boardComments);
    	boardComments.setBoardContents(null);
    }
    
    
    // イメージ追加
    public void addImage(BoardImages image) {
        images.add(image);
        image.setBoardContents(this);
    }
    
    // イメージ削除
    public void removeImage(BoardImages image) {
        images.remove(image);
        image.setBoardContents(null);
    }
    
}
