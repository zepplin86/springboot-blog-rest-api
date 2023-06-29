package com.springboot.blog.service;

import java.security.Principal;

public interface LikeService {

    void createLike(Long postId, Principal principal);

    void deleteLike(Long postId, Long likeId);
}
