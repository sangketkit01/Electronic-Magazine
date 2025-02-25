package com.src.electronicmagazine.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationType(
    @Expose
    @SerializedName("notification_type_id") val notificationTypeId : Int,

    @Expose
    @SerializedName("notification_type_name") val notificationTypeName : String
) : Parcelable
