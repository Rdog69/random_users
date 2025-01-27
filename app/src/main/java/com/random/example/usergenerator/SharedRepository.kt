package com.random.example.usergenerator

import android.content.Context
import androidx.preference.PreferenceManager
import com.random.example.usergenerator.network.NetworkLayer
import com.random.example.usergenerator.network.response.RandomUserResponse
class SharedRepository(private val context: Context) {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val selectedCountries = sharedPreferences.getStringSet("countries", emptySet())
    val selectedGender = sharedPreferences.getString("gender", "male")
    suspend fun getUsers(): RandomUserResponse?{
        val request = NetworkLayer.apiClient.getUsers()
        if (request.failed) {
            return null
        }
        if (!request.isSuccessful){
            return null
        }

        return request.body
    }
    suspend fun getPageUsers(pageIndex: Int): RandomUserResponse?{
        val request = NetworkLayer.apiClient.getUsersPage(pageIndex,selectedCountries?.toList(),selectedGender)
        if (!request.isSuccessful){
            return null
        }
        return request.body
    }
}
