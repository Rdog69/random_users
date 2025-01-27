package com.random.example.usergenerator

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.random.example.usergenerator.network.UsersPagingSource
import com.random.example.usergenerator.network.local.AppDatabase
import com.random.example.usergenerator.network.local.PersonEntity
import com.random.example.usergenerator.network.local.PersonRepository
import com.random.example.usergenerator.network.response.RandomUserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : ViewModel(){
    val repository = SharedRepository(application)
    private val db = AppDatabase.getDatabase(application)
    val personRepository = PersonRepository(db)

    private val _usersLiveData = MutableLiveData<RandomUserResponse>()
    val usersLiveData : LiveData<RandomUserResponse> = _usersLiveData
    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(
            pageSize = 10
        )
    ) {
        UsersPagingSource(repository)
    }.flow
        .cachedIn(viewModelScope)

     fun deleteSavedPerson(person: PersonEntity){
         viewModelScope.launch(Dispatchers.IO) {
             personRepository.deletePerson(person)
         }
    }
    fun refreshUsers(){
        viewModelScope.launch {
            val response = repository.getUsers()
            _usersLiveData.postValue(response)
        }
    }
}