package com.sousaarthur.blog.modules.cloudinary.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private Cloudinary cloudinary;

   public CloudinaryService(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }

    public Map uploadImage(MultipartFile file) {
       try {
           return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }

    public String getPublicUrl(String publicId){
       return cloudinary.url().generate(publicId);
    }

}
