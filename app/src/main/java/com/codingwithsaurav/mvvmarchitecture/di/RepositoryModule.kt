package com.codingwithsaurav.mvvmarchitecture.di

import com.codingwithsaurav.mvvmarchitecture.post.domain.repository.PostDataRepository
import com.codingwithsaurav.mvvmarchitecture.post.domain.repository.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPostRepository(postDataRepository: PostDataRepository): PostRepository

}