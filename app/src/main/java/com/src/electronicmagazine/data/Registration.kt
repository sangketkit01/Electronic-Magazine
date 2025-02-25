package com.src.electronicmagazine.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp

@Parcelize
data class Registration(
    @Expose
    @SerializedName("registration_id") val registrationId : Int,

    @Expose
    @SerializedName("user_id") val userId : Int,

    @Expose
    @SerializedName("fullname") val fullName : String,

    @Expose
    @SerializedName("id_card") val idCard : String,

    @Expose
    @SerializedName("id_card_path") val idCardPath : String?,

    @Expose
    @SerializedName("email") val email : String,

    @Expose
    @SerializedName("phone") val phone : String,

    @Expose
    @SerializedName("address") val address : String,

    @Expose
    @SerializedName("bio") val bio : String,

    @Expose
    @SerializedName("status") val status : String,

    @Expose
    @SerializedName("applied_at") val appliedAt : Timestamp,

    @Expose
    @SerializedName("reviewed_at") val reviewedAt : Timestamp
) : Parcelable
