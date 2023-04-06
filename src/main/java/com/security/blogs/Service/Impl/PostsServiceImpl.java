package com.security.blogs.Service.Impl;

import com.security.blogs.Dao.CategoryRepository;
import com.security.blogs.Dao.PostRepository;
import com.security.blogs.Dao.UserRepo;
import com.security.blogs.Exceptions.ResourceNotFoundException;
import com.security.blogs.Model.Category;
import com.security.blogs.Model.Posts;
import com.security.blogs.Model.User;
import com.security.blogs.Payloads.PostDto;
import com.security.blogs.Payloads.PostPaginationResponse;
import com.security.blogs.Service.PostsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public PostPaginationResponse getAllPosts(int pageNumber, int pageSize) {

        // Implementing Pagination
        Pageable p = PageRequest.of(pageNumber, pageSize);
        Page<Posts> pagePost = this.postRepo.findAll(p);
        List<Posts> allPosts = pagePost.getContent();

        List<PostDto> allPostsDto = allPosts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        // Setting all the postPage data to the PostPaginationResponse object to get the Response through the API
        PostPaginationResponse postPaginationResponse = new PostPaginationResponse();
        postPaginationResponse.setContent(allPostsDto); //Set all post in the content
        postPaginationResponse.setPageNumber(pagePost.getNumber());
        postPaginationResponse.setPageSize(pagePost.getSize());
        postPaginationResponse.setTotalElements(pagePost.getTotalElements());
        postPaginationResponse.setTotalPages(pagePost.getTotalPages());
        postPaginationResponse.setLast(pagePost.isLast());

        return postPaginationResponse;
    }

    @Override
    public PostDto postById(int postId) {
        Posts postById = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "postId", postId));
        return this.modelMapper.map(postById, PostDto.class);
    }

    @Override
    public PostDto createPost(PostDto postDto, int userId, int category_id) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        Category category = categoryRepo.findById(category_id).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", category_id));

        Posts post = this.modelMapper.map(postDto, Posts.class);

        post.setUser(user);
        post.setCategory(category);
        post.setImage("default.png");
        post.setAdded_date(new Date());

        Posts createdPost = postRepo.save(post);

        return this.modelMapper.map(createdPost, PostDto.class);
        //return createdPost;
    }

    @Override
    public PostDto updatePost(PostDto postDto, int postId) {

        Posts postById = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "postId", postId));

        postById.setPost_title(postDto.getPost_title());
        postById.setPost_content(postDto.getPost_content());
        postById.setImage(postDto.getImage());
        postById.setAdded_date(new Date());

        Posts updatedPost = this.postRepo.save(postById);

        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(int postId) {

        Posts post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "postId", postId));

        this.postRepo.delete(post);
    }

    @Override
    public List<PostDto> getPostsByUser(int userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        List<Posts> allPostsByUser = this.postRepo.findByUser(user);

        List<PostDto> allPostsByUserDto = allPostsByUser.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return allPostsByUserDto;
    }

    @Override
    public List<PostDto> getPostsByCategory(int categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        System.out.println(String.format("Selected Category is - \'%s\'", category.getCategory_title()));

        List<Posts> allPostsByCategory = this.postRepo.findByCategory(category);

        List<PostDto> allPostsByCategoryDto = allPostsByCategory.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return allPostsByCategoryDto;
    }

    @Override
    public List<PostDto> getPostByUserAndCategory(int userId, int categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","categoryId", categoryId));

        List<Posts> allPostsByUserAndCategory = this.postRepo.findByUserAndCategory(user, category);

        List<PostDto> allPostsByUserAndCategoryDto = allPostsByUserAndCategory.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return allPostsByUserAndCategoryDto;
    }
}
