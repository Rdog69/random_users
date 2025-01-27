package com.random.example.usergenerator.network

import com.random.example.usergenerator.network.response.RandomUserResponse
import com.random.example.usergenerator.network.response.SimpleResponse
import retrofit2.Response

class ApiClient(
    private val randomUserService: RandomUserService
) {
    suspend fun getUsers() : SimpleResponse<RandomUserResponse>{
        return safeApiCall { randomUserService.getUsers() }
    }
    //SEED causes the gender query to not work
    suspend fun getUsersPage(pageIndex: Int, nationalities: List<String>? = null, gender: String? = null): SimpleResponse<RandomUserResponse> {
        return safeApiCall { randomUserService.getUsersPage(pageIndex,"",10, nationalities = nationalities,
            gender = gender) }
    }
    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}