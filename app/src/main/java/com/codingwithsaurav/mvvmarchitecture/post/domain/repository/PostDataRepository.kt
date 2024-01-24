package com.codingwithsaurav.mvvmarchitecture.post.domain.repository

import com.codingwithsaurav.mvvmarchitecture.post.domain.model.PostResponse
import com.codingwithsaurav.mvvmarchitecture.post.domain.service.PostApiService
import javax.inject.Inject

class PostDataRepository @Inject constructor(
    private val postApiService: PostApiService
) : PostRepository {

    override suspend fun getPosts(): PostResponse {
        return postApiService.getPosts()
    }

}