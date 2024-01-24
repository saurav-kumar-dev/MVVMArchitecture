package com.codingwithsaurav.mvvmarchitecture.di

import com.codingwithsaurav.mvvmarchitecture.BuildConfig
import com.codingwithsaurav.mvvmarchitecture.post.domain.service.PostApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Provides
    fun providePostApiService(retrofit: Retrofit): PostApiService {
        return retrofit.create(PostApiService::class.java)
    }

}