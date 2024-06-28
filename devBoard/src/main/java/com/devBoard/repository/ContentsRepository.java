package com.devBoard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devBoard.domain.BoardContents;

@Repository
public interface ContentsRepository extends JpaRepository<BoardContents, Integer>{
	Page<BoardContents> findAll(Pageable pageable);
	Page<BoardContents> findAll(Specification<BoardContents> spec, Pageable pageable);
}
