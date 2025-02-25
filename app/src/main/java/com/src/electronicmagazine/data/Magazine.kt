package com.src.electronicmagazine.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.sql.Date
import java.sql.Timestamp

@Parcelize
data class Magazine(
    @Expose
    @SerializedName("magazine_id") val magazineId : Int,

    @Expose
    @SerializedName("title") val title : String,

    @Expose
    @SerializedName("description") val description : String,

    @Expose
    @SerializedName("status") val status : String,

    @Expose
    @SerializedName("published_date") val publishedDate : Date,

    @Expose
    @SerializedName("writer_id") val writerId : Int,

    @Expose
    @SerializedName("category_id") val categoryId : Int,

    @Expose
    @SerializedName("image_id") val imageId : Int,

    @Expose
    @SerializedName("created_at") val createdAt : Timestamp

) : Parcelable
