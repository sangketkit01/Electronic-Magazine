package com.src.electronicmagazine.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp

@Parcelize
data class Notification(
    @Expose
    @SerializedName("notification_id") val notificationId : Int,

    @Expose
    @SerializedName("notification_title") val notificationTitle : String,

    @Expose
    @SerializedName("notification_detail") val notificationDetail : String,

    @Expose
    @SerializedName("user_id") val userId : Int,

    @Expose
    @SerializedName("notification_type_id") val notificationTypeId : Int,

    @Expose
    @SerializedName("created_at") val createdAt : Timestamp
) : Parcelable
