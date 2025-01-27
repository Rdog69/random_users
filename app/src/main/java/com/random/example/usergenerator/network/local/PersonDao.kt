package com.random.example.usergenerator.network.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PersonDao {

    @Query("SELECT * FROM person")
    fun getAll(): LiveData<List<PersonEntity>>

    @Query("SELECT * FROM person WHERE email = :email LIMIT 1")
    fun getPersonByEmail(email: String): PersonEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg users: PersonEntity)

    @Delete
    fun delete(person: PersonEntity)

    @Query("SELECT * FROM person")
    fun export():List<PersonEntity>
}
