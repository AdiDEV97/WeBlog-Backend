package com.security.blogs.Service.Impl;

import com.security.blogs.Dao.PostRepository;
import com.security.blogs.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private PostRepository postRepo;

    @Override
    public String uploadingImage(String path, MultipartFile image) throws IOException {

        // File Name
        String imageName = image.getOriginalFilename();

        // Full File Path
        String randomId = UUID.randomUUID().toString(); // This is to create random Id to maintain the unique name of the image
        String imageNameWithRandomId = randomId.concat(imageName.substring(imageName.lastIndexOf('.'))); // This is to concatenate the random Id to name of the image with its extension name
        System.out.println("ImageNameWithRandomId - " + imageNameWithRandomId);
        String imageFullPath = path + File.separator + imageNameWithRandomId;
        System.out.println("ImageFullPath - " + imageFullPath);

        // Create a folder if not exist
        File file = new File(path);
        if(!file.exists()) {
            file.mkdir();
        }

        // File Copy
        if(imageName.substring(imageName.lastIndexOf('.')).equals(".jpg") || imageName.substring(imageName.lastIndexOf('.')).equals(".png") || imageName.substring(imageName.lastIndexOf('.')).equals(".JPG") || imageName.substring(imageName.lastIndexOf('.')).equals(".PNG")) {
            Files.copy(image.getInputStream(), Paths.get(imageFullPath));
        }
        else {
            System.out.println("File should be Image!!");
            throw new RuntimeException("File should be Image!!");
        }

        return imageNameWithRandomId;
    }

    @Override
    public InputStream getImage(String path, String imageName) throws FileNotFoundException {

        String fullPath = path + File.separator + imageName;

        InputStream inputStream = new FileInputStream(fullPath);

        return inputStream;
    }
}
