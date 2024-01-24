package com.codingwithsaurav.mvvmarchitecture.common

data class BaseApiStatus<T>(
    val data: T? = null,
    val errorMessage: String?= null,
    val isLoading: Boolean = false
)