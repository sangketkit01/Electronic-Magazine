package com.src.electronicmagazine.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp

@Parcelize
data class User(
    @Expose
    @SerializedName("user_id") val userId : Int,

    @Expose
    @SerializedName("username") val username : String,

    @Expose
    @SerializedName("email") val email : String,

    @Expose
    @SerializedName("password") val password : String,

    @Expose
    @SerializedName("name") val name : String,

    @Expose
    @SerializedName("role") val role : String,

    @Expose
    @SerializedName("profile_path") val profilePath : String?,

    @Expose
    @SerializedName("created_at") val createdAt : Timestamp
) : Parcelable
