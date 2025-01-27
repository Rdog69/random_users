package com.random.example.usergenerator.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.preference.PreferenceManager
import com.random.example.usergenerator.SharedRepository
import com.random.example.usergenerator.network.response.PersonInfo

 class UsersPagingSource ( val repo : SharedRepository
 )   : PagingSource<Int, PersonInfo>() {

     override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PersonInfo> {


         return try {
             // Start refresh at page 1 if undefined.
             val nextPageNumber = params.key ?: 1
             val previousKey = if (nextPageNumber == 1) null else nextPageNumber - 1
             val response = repo.getPageUsers(nextPageNumber)
             LoadResult.Page(
                 data = response!!.results,
                 prevKey = previousKey,
                 nextKey = nextPageNumber + 1
             )
         } catch (e: Exception) {
             LoadResult.Error(e)
         }

     }
    override fun getRefreshKey(state: PagingState<Int, PersonInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    }


 }