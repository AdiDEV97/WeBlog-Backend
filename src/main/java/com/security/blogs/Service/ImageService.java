package com.security.blogs.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface ImageService {

    String uploadingImage(String path, MultipartFile image) throws IOException;

    InputStream getImage(String path, String imageName) throws FileNotFoundException;

}
