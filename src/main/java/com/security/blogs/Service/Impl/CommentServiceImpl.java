package com.security.blogs.Service.Impl;

import com.security.blogs.Dao.CommentRepo;
import com.security.blogs.Dao.PostRepository;
import com.security.blogs.Dao.UserRepo;
import com.security.blogs.Exceptions.ResourceNotFoundException;
import com.security.blogs.Model.Comment;
import com.security.blogs.Model.Posts;
import com.security.blogs.Model.User;
import com.security.blogs.Payloads.CommentDto;
import com.security.blogs.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> allComments = this.commentRepo.findAll();
        return allComments.stream().map((comment) -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllCommentByPost(int posts_id) {

        Posts post = this.postRepo.findById(posts_id).orElseThrow(() -> new ResourceNotFoundException("Posts", "post_id", posts_id));

        List<Comment> allCommentsByPost = this.commentRepo.findByPosts(post);
        return allCommentsByPost.stream().map((comment -> this.modelMapper.map(comment, CommentDto.class))).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(int id) {
        Comment commentById = this.commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comments", "id", id));
        return this.modelMapper.map(commentById, CommentDto.class);
    }

    @Override
    public CommentDto createCommentByPosts(CommentDto commentDto, int user_id, int post_id) {

        User user = this.userRepo.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "user_id", user_id));
        Posts post = this.postRepo.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("Posts", "post_id", post_id));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment.setUser(user);
        comment.setPosts(post);
        comment.setCommentAddedDate(new Date());

        Comment newComment = this.commentRepo.save(comment);
        return this.modelMapper.map(newComment, CommentDto.class);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, int commentId) {

        Comment commentById = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
        commentById.setComment_content(commentDto.getComment_content());
        commentById.setCommentAddedDate(new Date());

        Comment savedComment = this.commentRepo.save(commentById);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteCommentById(int commentId) {

        Comment commentById = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
        this.commentRepo.delete(commentById);
    }
}
