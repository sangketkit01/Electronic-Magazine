package com.src.electronicmagazine.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    @Expose
    @SerializedName("category_id") val categoryId : Int,

    @Expose
    @SerializedName("name") val name : String
) : Parcelable
