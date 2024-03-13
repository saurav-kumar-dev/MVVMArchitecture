package com.codingwithsaurav.mvvmarchitecture.common

sealed class NetworkResult<T>(val data: T?= null, val message: String?= null) {
    class Success<T>(data: T): NetworkResult<T>(data)
    class Error<T>(errorMessage: String?): NetworkResult<T>(data = null, errorMessage)
    class Loading<T>: NetworkResult<T>()
}