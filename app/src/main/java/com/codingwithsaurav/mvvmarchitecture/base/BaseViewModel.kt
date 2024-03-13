package com.codingwithsaurav.mvvmarchitecture.base

import androidx.lifecycle.ViewModel
import com.codingwithsaurav.mvvmarchitecture.common.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

open class BaseViewModel() : ViewModel() {

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall:suspend ()-> Response<T>
    ): Flow<NetworkResult<T>>  = flow {
        emit(NetworkResult.Loading<T>())
        try {
            val response = apiCall()
            if (response.isSuccessful){
                val data = response.body()
                if(data != null){
                    emit(NetworkResult.Success<T>(data = data))
                }else{
                    val error = response.errorBody()
                    if(error != null){
                        emit(NetworkResult.Error<T>(errorMessage = error.string()?:"something went wrong"))
                    }else{
                        emit(NetworkResult.Error<T>(errorMessage = "something went wrong"))
                    }
                }
            }else{
                emit(NetworkResult.Error<T>(response.errorBody().toString()))
            }
        } catch (e: HttpException) {
             emit(NetworkResult.Error<T>(errorMessage = e.response()?.errorBody()?.string()?:(e.localizedMessage?:"something went wrong")))
        } catch (e: IOException) {
            emit(NetworkResult.Error<T>(errorMessage = e.localizedMessage?:"something went wrong"))
        }catch (e: Throwable) {
            emit(NetworkResult.Error<T>(errorMessage = e.localizedMessage?:"something went wrong"))
        }
    }.catch { e->
        e.printStackTrace()
        emit(NetworkResult.Error<T>(e.toString()))
    }.flowOn(dispatcher)


    override fun onCleared() {
        super.onCleared()
    }

}