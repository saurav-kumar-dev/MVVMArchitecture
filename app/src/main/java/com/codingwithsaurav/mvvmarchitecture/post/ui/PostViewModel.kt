package com.codingwithsaurav.mvvmarchitecture.post.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.codingwithsaurav.mvvmarchitecture.base.BaseViewModel
import com.codingwithsaurav.mvvmarchitecture.common.BaseApiStatus
import com.codingwithsaurav.mvvmarchitecture.common.NetworkResult
import com.codingwithsaurav.mvvmarchitecture.post.domain.model.PostResponse
import com.codingwithsaurav.mvvmarchitecture.post.domain.service.PostApiService
import com.codingwithsaurav.mvvmarchitecture.post.domain.usecase.PostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val useCase: PostUseCase,
    private val postApiService: PostApiService
) : BaseViewModel() {

    private val _postStatus: MutableLiveData<BaseApiStatus<PostResponse>> = MutableLiveData()
    val postStatus: LiveData<BaseApiStatus<PostResponse>> get() = _postStatus

    fun getPostList(){
        viewModelScope.launch {
            safeApiCall { postApiService.getPostList() }.collect {
                when (it) {
                    is NetworkResult.Loading -> _postStatus.postValue(BaseApiStatus(isLoading = true))
                    is NetworkResult.Success -> _postStatus.postValue(BaseApiStatus(data = it.data))
                    is NetworkResult.Error -> _postStatus.postValue(BaseApiStatus(errorMessage = it.message))
                }
            }
        }
    }

    fun getPosts() {
        viewModelScope.launch {
            useCase.getPosts().collect {
                when (it) {
                    is NetworkResult.Loading -> _postStatus.postValue(BaseApiStatus(isLoading = true))
                    is NetworkResult.Success -> _postStatus.postValue(BaseApiStatus(data = it.data))
                    is NetworkResult.Error -> _postStatus.postValue(BaseApiStatus(errorMessage = it.message))
                }
            }
        }
    }

}