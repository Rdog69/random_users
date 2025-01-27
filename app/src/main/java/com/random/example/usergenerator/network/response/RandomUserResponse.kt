package com.random.example.usergenerator.network.response

data class RandomUserResponse(
    val results: List<PersonInfo>,
    val info: Info
){

    data class Info(
        val seed: String,
        val results: Int,
        val page: Int,
        val version: String
    )
}

