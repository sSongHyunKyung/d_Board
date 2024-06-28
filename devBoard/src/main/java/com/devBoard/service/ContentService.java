package com.devBoard.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.devBoard.domain.BoardContents;
import com.devBoard.domain.BoardImages;
import com.devBoard.domain.FormContent;
import com.devBoard.repository.ContentsRepository;
import com.devBoard.repository.ImagesRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentService {

	private final ContentsRepository contentRepository;
	private final ImagesRepository imagesRepository;
	
	
	//登録
	public void contentCreate(FormContent formContent, MultipartFile imageFile) {
		BoardContents contents = new BoardContents();
		contents.setContentTitle(formContent.getContentTitle());
		contents.setContentUser(formContent.getContentUser());
		contents.setContentPasswd(formContent.getContentPasswd());
		contents.setContentContents(formContent.getContentContents());
		contents.setContentAt(LocalDateTime.now());
		
		this.contentRepository.save(contents);
		
		
		//imageFile処理
		if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
                String uploadDir = "board-images/" + contents.getContentId(); 
                String filePath = Paths.get(uploadDir, fileName).toString();

                // 파일 저장
                Files.createDirectories(Paths.get(uploadDir));
                Path targetLocation = Paths.get(filePath);
                Files.copy(imageFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                // 이미지 정보 저장
                BoardImages boardImage = new BoardImages();
                boardImage.setBoardContents(contents);
                boardImage.setImageFilename(fileName);
                boardImage.setImageUploadPath(uploadDir);

                this.imagesRepository.save(boardImage);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	}
	
	
	//修正
	public void modify(BoardContents boardContents, FormContent formContent) {
		boardContents.setContentTitle(formContent.getContentTitle());
		boardContents.setContentContents(formContent.getContentContents());
		this.contentRepository.save(boardContents);
	}
	
	 
	//削除
    public void delete(BoardContents boardContents) {
    	this.contentRepository.delete(boardContents);
    }
    
    //ページイン
    public Page<BoardContents> getList(int page, String kw, String category) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("contentAt"));
    	Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
    	Specification<BoardContents> spec = search(kw, category);
    	return this.contentRepository.findAll(spec, pageable);
    }
    
    //検索
    private Specification<BoardContents> search(String kw, String category) {
    	return new Specification<>() {
			private static final long searialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<BoardContents> board, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true); 
                // カテゴリー
                if ("category1".equals(category)) {
                    // タイトル検索
                    return cb.like(board.get("contentTitle"), "%" + kw + "%");
                } else if ("category2".equals(category)) {
                    // 内容検索
                    return cb.like(board.get("contentContents"), "%" + kw + "%");
                } else {
                    // 全て
                    return cb.or(
                        cb.like(board.get("contentTitle"), "%" + kw + "%"),
                        cb.like(board.get("contentContents"), "%" + kw + "%")
                    );
                }
			}
		};
    }


    // イメージ修正
	public void updateImage(BoardContents boardContents, MultipartFile imageFile) {
	    try {
	        if (imageFile != null && !imageFile.isEmpty()) {
	            String imagePath = "C:/Dev/workspace/board/devBoard/board-images/" 
	                                + boardContents.getContentId() 
	                                + "/" + imageFile.getOriginalFilename();
	            Path path = Paths.get(imagePath);

	            // dir存在
	            if (!Files.exists(path.getParent())) {
	                Files.createDirectories(path.getParent());
	            }

	            // ファイル保存
	            Files.write(path, imageFile.getBytes());

	            // 新しいイメージファイルUpdate
	            BoardImages boardImage = new BoardImages();
	            boardImage.setBoardContents(boardContents);
	            boardImage.setImageFilename(imageFile.getOriginalFilename());
	            boardImage.setImageUploadPath(imagePath); // 경로 설정 추가

	            // 既存のイメージがある場合
	            boardContents.getImages().clear(); 
	            boardContents.getImages().add(boardImage);

	            // 修正
	            this.contentRepository.save(boardContents);
	        } else {
	            System.out.println("イメージファイルなし");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	
	}
}
