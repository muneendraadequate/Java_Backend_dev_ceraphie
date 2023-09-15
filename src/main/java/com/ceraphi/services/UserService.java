package com.ceraphi.services;

import com.ceraphi.utils.UserResponse;

public interface UserService {
    UserResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}


