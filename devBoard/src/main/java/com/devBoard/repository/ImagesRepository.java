package com.devBoard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devBoard.domain.BoardImages;

@Repository
public interface ImagesRepository extends JpaRepository<BoardImages, Long>{
	List<BoardImages> findByBoardContentsContentId(Long contentId);
}
