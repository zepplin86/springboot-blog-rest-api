package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Like;
import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.LikeRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class LikeServiceImpl implements LikeService {
    private LikeRepository likeRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    public LikeServiceImpl(LikeRepository likeRepository,
                           PostRepository postRepository,
                           UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createLike(Long postId, Principal principal) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));

        String name = principal.getName();
        User user = userRepository.findByEmail(name).orElseThrow(()-> new BlogAPIException(HttpStatus.NOT_FOUND, "User is not found"));

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        likeRepository.save(like);

    }

    @Override
    public void deleteLike(Long postId, Long likeId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        Like like = likeRepository.findById(likeId).orElseThrow(()-> new ResourceNotFoundException("Like", "id", likeId));

        likeRepository.delete(like);
    }
}
