package com.random.example.usergenerator.network.local

import androidx.lifecycle.LiveData
import com.random.example.usergenerator.network.response.PersonInfo
import com.random.example.usergenerator.utils.toEntity


// For saving users
class PersonRepository(private val db: AppDatabase) {

    suspend fun addPerson(personInfo: PersonInfo) {
        val entity = personInfo.toEntity()
        val existingPerson = db.personDao().getPersonByEmail(entity.email)
        if (existingPerson == null) {
            db.personDao().insertAll(entity)
        }
    }
    fun deletePerson(person: PersonEntity){
        db.personDao().delete(person)
    }
    fun getPersonByEmail(email: String): PersonEntity? {
        return db.personDao().getPersonByEmail(email)
    }

    fun getAll(): LiveData<List<PersonEntity>> {
        return db.personDao().getAll()
    }
    fun export(): List<PersonEntity> {
        return db.personDao().export()
    }

}
