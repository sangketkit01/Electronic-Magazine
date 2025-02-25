package com.src.electronicmagazine.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp

@Parcelize
data class Article(
    @Expose
    @SerializedName("article_id") val articleId : Int,

    @Expose
    @SerializedName("magazine_id") val magazineId : Int,

    @Expose
    @SerializedName("title") val title : String,

    @Expose
    @SerializedName("content") val content : String,

    @Expose
    @SerializedName("updated_at") val updatedAt : Timestamp
) : Parcelable
