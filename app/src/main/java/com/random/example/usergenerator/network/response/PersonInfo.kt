package com.random.example.usergenerator.network.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PersonInfo(
    val gender: String = "",
    val name: NameInfo = NameInfo(),
    val location: LocationInfo = LocationInfo(),
    val email: String = "",
    val login: LoginInfo = LoginInfo(),
    val dob: DobInfo = DobInfo(),
    val registered: RegisteredInfo = RegisteredInfo(),
    val phone: String = "",
    val cell: String = "",
    val id: IdInfo = IdInfo(),
    val picture: PictureInfo = PictureInfo(),
    val nat: String = ""
) : Parcelable {
    @Parcelize
    data class NameInfo(
        val title: String = "",
        val first: String = "",
        val last: String = ""
    ) : Parcelable

    @Parcelize
    data class LocationInfo(
        val street: StreetInfo = StreetInfo(),
        val city: String = "",
        val state: String = "",
        val country: String = "",
        val postcode: String = "",
        val coordinates: CoordinatesInfo = CoordinatesInfo(),
        val timezone: TimezoneInfo = TimezoneInfo()
    ) : Parcelable

    @Parcelize
    data class StreetInfo(
        val number: Int = 0,
        val name: String = ""
    ) : Parcelable

    @Parcelize
    data class CoordinatesInfo(
        val latitude: String = "",
        val longitude: String = ""
    ) : Parcelable

    @Parcelize
    data class TimezoneInfo(
        val offset: String = "",
        val description: String = ""
    ) : Parcelable

    @Parcelize
    data class LoginInfo(
        val uuid: String = "",
        val username: String = "",
        val password: String = "",
        val salt: String = "",
        val md5: String = "",
        val sha1: String = "",
        val sha256: String = ""
    ) : Parcelable

    @Parcelize
    data class DobInfo(
        val date: String = "",
        val age: Int = 0
    ) : Parcelable

    @Parcelize
    data class RegisteredInfo(
        val date: String = "",
        val age: Int = 0
    ) : Parcelable

    @Parcelize
    data class IdInfo(
        val name: String = "",
        val value: String? = ""
    ) : Parcelable

    @Parcelize
    data class PictureInfo(
        val large: String = "",
        val medium: String = "",
        val thumbnail: String = ""
    ) : Parcelable
}
