package com.devBoard;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.devBoard.domain.BoardContents;
import com.devBoard.repository.ContentsRepository;

import jakarta.transaction.Transactional;



//@Transactional
@SpringBootTest
class DevBoardApplicationTests {

	
	
	@Autowired
	private ContentsRepository contentsRepository;

	
	@DisplayName("内容InsertTest")
	@Test
	void insertContents() {
			BoardContents b1 = new BoardContents();
			b1.setContentTitle("タイトル");
			b1.setContentUser("song");
			b1.setContentPasswd("12345678");
			b1.setContentContents("aopdjgposdajg");
			b1.setContentAt(LocalDateTime.now());
			this.contentsRepository.save(b1);
		
	}
	
//	@DisplayName("内容UpdateTest")
//	@Test
//	void updateContents() {
//		Optional<BoardContents> contents = this.contentsRepository.findById(3);
//		assertTrue(contents.isPresent());
//		BoardContents content = contents.get();
//		content.setContentTitle("修正されたタイトルでした。");
//		this.contentsRepository.save(content);
//	}
//	
//	
//	@DisplayName("内容DeleteTest")
//	@Test
//	void deleteContents() {
//		Optional<BoardContents> contents = this.contentsRepository.findById(5);
//		assertTrue(contents.isPresent());
//		BoardContents content = contents.get();
//		this.contentsRepository.delete(content);
//	}

}
