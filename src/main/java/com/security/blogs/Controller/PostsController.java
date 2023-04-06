package com.security.blogs.Controller;

import com.security.blogs.Model.ApiResponse;
import com.security.blogs.Model.Posts;
import com.security.blogs.Payloads.PostDto;
import com.security.blogs.Payloads.PostPaginationResponse;
import com.security.blogs.Service.ImageService;
import com.security.blogs.Service.PostsService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api/post")
public class PostsController {

    @Autowired
    private PostsService postsServ;

    @Autowired
    private ImageService imageServ;

    @Value("${blog.image}")
    private String path;

    // All Posts API with Pagination
    @GetMapping("/allPosts")
    public ResponseEntity<PostPaginationResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        PostPaginationResponse postsWithPagination = postsServ.getAllPosts(pageNumber, pageSize);
        return new ResponseEntity<PostPaginationResponse>(postsWithPagination, HttpStatus.OK);
    }


    @GetMapping("/postId/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable int postId) {
        PostDto postById = this.postsServ.postById(postId);

        if(postById == null) {
            return new ResponseEntity<PostDto>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<PostDto>(postById, HttpStatus.OK);
        }
    }


    @PostMapping("/user/{userId}/category/{categoryId}/new-post")
    public ResponseEntity<PostDto> createNewPost(@Valid @RequestBody PostDto postDto, @PathVariable("userId") int userId, @PathVariable("categoryId") int categoryId) {

        try {
            PostDto createdPost = postsServ.createPost(postDto, userId, categoryId);
            //return ResponseEntity.of(Optional.of(createdPost));
            return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<PostDto>((PostDto) null, HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PutMapping("/update/postId/{postId}")
    public ResponseEntity<ApiResponse> updatePost(@RequestBody PostDto postDto, @PathVariable int postId) {
        try {
            PostDto updatedPost = this.postsServ.updatePost(postDto, postId);
            return new ResponseEntity<ApiResponse>(new ApiResponse(String.format("The Data with title name - \'%s\' is updated successfully!!", updatedPost.getPost_title()), true), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ApiResponse>(new ApiResponse(String.format("Error!! The Data with title name - \'%s\' is not updated!!", postDto.getPost_title()), false), HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @DeleteMapping("/delete/postId/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId) {
        try {
            this.postsServ.deletePost(postId);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Post is Deleted successfully!!", true), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ApiResponse>(new ApiResponse("Post is NOT Deleted!!", false), HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @GetMapping("/byCategory/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getAllPostsByCategory(@Valid @PathVariable int categoryId) {
        List<PostDto> postsByCategory = postsServ.getPostsByCategory(categoryId);
        if(postsByCategory.isEmpty()) {
            System.out.println("Total Posts in the category are - " + postsByCategory.size());
            System.out.println("No post found related with this category!!");
            return new ResponseEntity<List<PostDto>>(HttpStatus.NOT_FOUND);
        }
        else{
            System.out.println("Total Posts in the category are - " + postsByCategory.size());
            return new ResponseEntity<List<PostDto>>(postsByCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/byUser/user/{userId}")
    public ResponseEntity<List<PostDto>> getAllPostsByUser(@Valid @PathVariable int userId) {
        List<PostDto> postsByUser = postsServ.getPostsByUser(userId);
        if(postsByUser.isEmpty()) {
            System.out.println("Total Posts with respect to this user are - " + postsByUser.size());
            System.out.println("No post found related with this user!!");
            return new ResponseEntity<List<PostDto>>(HttpStatus.NOT_FOUND);
        }
        else{
            System.out.println("Total Posts with respect to this user are - " + postsByUser.size());
            return new ResponseEntity<List<PostDto>>(postsByUser, HttpStatus.OK);
        }
    }

    // Get Post By User and Category
    @GetMapping("/byUser/{userId}/byCategory/{categoryId}")
    public ResponseEntity<List<PostDto>> getAllPostsByUserAndCategory(@Valid @PathVariable("userId") int userId, @PathVariable("categoryId") int categoryId) {
        List<PostDto> postsByUserAndCategory = this.postsServ.getPostByUserAndCategory(userId, categoryId);
        if(postsByUserAndCategory.isEmpty()) {
            System.out.println("Total Posts with respect to this user are - " + postsByUserAndCategory.size());
            System.out.println("No post found related with this user!!");
            return new ResponseEntity<List<PostDto>>(HttpStatus.NOT_FOUND);
        }
        else {
            System.out.println("Total Posts with respect to this user are - " + postsByUserAndCategory.size());
            return new ResponseEntity<List<PostDto>>(postsByUserAndCategory, HttpStatus.OK);
        }
    }

    // Post Image Upload
    @PostMapping("/image/upload/{post_id}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image, @PathVariable("post_id") int post_id) throws IOException {

        System.out.println("Image Path C - " + path);
        System.out.println("Image Multipart C - " + image);
        System.out.println("PostId C - " + post_id);

        PostDto postDto = this.postsServ.postById(post_id);

        String imageName = this.imageServ.uploadingImage(path, image);
        System.out.println("ImageName C - " + imageName);

        // Update imageName to the database
        postDto.setImage(imageName);
        PostDto updatePostWithImage = this.postsServ.updatePost(postDto, post_id);

        return new ResponseEntity<PostDto>(updatePostWithImage, HttpStatus.OK);
    }

    // Get (Stream) Image from Database
    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImageUrl(@PathVariable("imageName") String imageName, HttpServletResponse httpServletResponse) throws IOException {

        InputStream resource = this.imageServ.getImage(path, imageName); // Stream created
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE); // Set the Type of the Data (Stream) that we want to get
        StreamUtils.copy(resource, httpServletResponse.getOutputStream()); // Copy the stream data to the response

    }

}

