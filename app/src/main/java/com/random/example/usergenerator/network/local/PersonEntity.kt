package com.random.example.usergenerator.network.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index

@Entity(tableName = "person", indices = [Index(value = ["email"], unique = true)])
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,

    @ColumnInfo(name = "gender")
    val gender: String,

    @ColumnInfo(name = "first_name")
    val firstName: String,

    @ColumnInfo(name = "last_name")
    val lastName: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "phone")
    val phone: String,

    @ColumnInfo(name = "cell")
    val cell: String,

    @ColumnInfo(name = "picture_url")
    val pictureUrl: String,

    @ColumnInfo(name = "dob")
    val dob: String,

    @ColumnInfo(name = "dob_age")
    val dobAge: Int,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "iconState")
    var iconState: Boolean = false // State to check if user is saved
)
