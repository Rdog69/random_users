package com.random.example.usergenerator.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkLayer {

    val moshi : Moshi  = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val randomUserService by lazy {
        retrofit.create(RandomUserService::class.java)
    }
    val apiClient = ApiClient(randomUserService)
}