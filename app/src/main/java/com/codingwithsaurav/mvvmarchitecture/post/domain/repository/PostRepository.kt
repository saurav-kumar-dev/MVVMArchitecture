package com.codingwithsaurav.mvvmarchitecture.post.domain.repository

import com.codingwithsaurav.mvvmarchitecture.post.domain.model.PostResponse

interface PostRepository {

    suspend fun getPosts(): PostResponse

}