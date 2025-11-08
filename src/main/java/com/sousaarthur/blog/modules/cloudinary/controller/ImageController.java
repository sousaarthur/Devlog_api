package com.sousaarthur.blog.modules.cloudinary.controller;

import com.sousaarthur.blog.exception.EventNotFoundException;
import com.sousaarthur.blog.modules.cloudinary.dto.CloudinaryDTO;
import com.sousaarthur.blog.modules.cloudinary.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("api/images")
public class ImageController {

    @Autowired
    private CloudinaryService service;
    @Autowired
    private MessageSource messageSource;

    @PostMapping("/upload")
    public ResponseEntity<CloudinaryDTO> upload(@RequestParam("file")MultipartFile file) throws IOException {
        if(file.isEmpty()) {
            throw new EventNotFoundException(
                    messageSource.getMessage("empty.file", null, LocaleContextHolder.getLocale())
            );
        }
        Map result = service.uploadImage(file);
        String url = (String) result.get("url");
        String createdAt = (String) result.get("created_at");

        return ResponseEntity.ok(new CloudinaryDTO(url,createdAt));
    }

}
