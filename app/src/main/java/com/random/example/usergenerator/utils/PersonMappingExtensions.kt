package com.random.example.usergenerator.utils

import com.random.example.usergenerator.network.local.PersonEntity
import com.random.example.usergenerator.network.response.PersonInfo

// Mapping from PersonInfo to PersonEntity
fun PersonInfo.toEntity(): PersonEntity {
    return PersonEntity(
        uid = 0, // Assuming auto-generation of primary key uid
        gender = this.gender,
        firstName = this.name.first,
        lastName = this.name.last,
        email = this.email,
        phone = this.phone,
        cell = this.cell,
        pictureUrl = this.picture.medium,
        dob = this.dob.date,
        dobAge = this.dob.age,
        address = "${this.location.street.number} ${this.location.street.name}, ${this.location.city}, ${this.location.state}, ${this.location.country}",
        username = this.login.username,
        password = this.login.password
    )
}