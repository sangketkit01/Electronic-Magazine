package com.src.electronicmagazine.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp

@Parcelize
data class Favorite(
    @Expose
    @SerializedName("user_id") val userId : Int,

    @Expose
    @SerializedName("magazine_id") val magazineId : Int,

    @Expose
    @SerializedName("created_at") val createdAt : Timestamp
) : Parcelable
