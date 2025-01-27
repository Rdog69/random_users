package com.random.example.usergenerator.network

import com.random.example.usergenerator.network.response.RandomUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RandomUserService {
    @GET("?results=25")
   suspend fun getUsers()
        :Response<RandomUserResponse>


    @GET("api/?gender=female")
    suspend fun getUsersPage(
        @Query("page") pageIndex: Int , @Query("seed") seed : String , @Query("results")numberOfResponses: Int, @Query("nat") nationalities: List<String>? = null,
        @Query("gender") gender: String? = null
    ): Response<RandomUserResponse>
}