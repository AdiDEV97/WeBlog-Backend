package com.security.blogs.Controller;

import com.security.blogs.Model.ApiResponse;
import com.security.blogs.Model.Posts;
import com.security.blogs.Payloads.CommentDto;
import com.security.blogs.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentServ;

    @GetMapping("/all-comments")
    public ResponseEntity<List<CommentDto>> getAllCommentsData() {
        List<CommentDto> allComments = this.commentServ.getAllComments();
        if(allComments.isEmpty()) {
            return new ResponseEntity<List<CommentDto>>(HttpStatus.NOT_FOUND);
        }
        else {
            System.out.println("Total comments are - " + allComments.size());
            return new ResponseEntity<List<CommentDto>>(allComments, HttpStatus.OK);
        }
    }


    @GetMapping("/by-post/{postId}/all-comments")
    public ResponseEntity<List<CommentDto>> getAllCommentsDataByPost(@PathVariable("postId") int post_id) {
        List<CommentDto> allCommentsByPost = this.commentServ.getAllCommentByPost(post_id);

        if(allCommentsByPost.isEmpty()) {
            return new ResponseEntity<List<CommentDto>>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<List<CommentDto>>(allCommentsByPost, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/user/{user_id}/post/{post_id}/new-comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDto> addNewComment(@RequestBody CommentDto commentDto, @PathVariable("user_id") int user_id,@PathVariable("post_id") int post_id) {
        try {
            CommentDto newComment = this.commentServ.createCommentByPosts(commentDto, user_id, post_id);
            return new ResponseEntity<CommentDto>(newComment, HttpStatus.CREATED);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CommentDto>(HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @PutMapping("/{comment_id}/update-comment")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable("comment_id") int comment_id) {
        try {
            CommentDto updatedComment = this.commentServ.updateComment(commentDto, comment_id);
            return new ResponseEntity<CommentDto>(updatedComment, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CommentDto>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{comment_id}/delete-comment")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("comment_id") int comment_id) {
        try {
            this.commentServ.deleteCommentById(comment_id);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Comment with id - " + comment_id +"is deleted successfully!!", true), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ApiResponse>(new ApiResponse("Comment is not deleted!!", true), HttpStatus.BAD_REQUEST);
        }
    }

}
