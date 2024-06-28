package com.devBoard.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class ImageController {

    @GetMapping("/{contentId}/{fileName}")
    public ResponseEntity<Resource> serveFile(@PathVariable("contentId") String contentId, @PathVariable("fileName") String fileName) {
        try {

            Path file = Paths.get("C:/Dev/workspace/board/devBoard/board-images/")
                              .resolve(contentId)
                              .resolve(fileName);
            
            System.out.println(file.toString());

            Resource resource = new UrlResource(file.toUri());
            System.out.println(resource.toString());
            
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity
                        .ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg") 
                        .body(resource);
            } else {
                throw new RuntimeException("ファイル取得できない。");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("エラー発生： " + e.getMessage());
        }
    }
}
