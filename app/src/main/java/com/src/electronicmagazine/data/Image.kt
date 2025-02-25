package com.src.electronicmagazine.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    @Expose
    @SerializedName("image_id") val imageId : Int,

    @Expose
    @SerializedName("article_id") val articleId : Int,

    @Expose
    @SerializedName("image_path") val imagePath : Int
) : Parcelable
