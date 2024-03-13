package com.codingwithsaurav.mvvmarchitecture.post.domain.service

import com.codingwithsaurav.mvvmarchitecture.post.domain.model.PostResponse
import retrofit2.Response
import retrofit2.http.GET

interface PostApiService {

    @GET("/posts")
    suspend fun getPostList(): Response<PostResponse>
    @GET("/posts")
    suspend fun getPosts(): PostResponse

}