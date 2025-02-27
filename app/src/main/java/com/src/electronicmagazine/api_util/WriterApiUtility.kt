package com.src.electronicmagazine.api_util

import com.google.gson.JsonObject
import com.src.electronicmagazine.api.UserAPI
import com.src.electronicmagazine.api.WriterAPI
import com.src.electronicmagazine.data.Article
import com.src.electronicmagazine.data.Category
import com.src.electronicmagazine.data.Favorite
import com.src.electronicmagazine.data.Magazine
import com.src.electronicmagazine.data.Registration
import com.src.electronicmagazine.data.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.sql.Timestamp

object WriterClient{
    val instance : WriterAPI by lazy { WriterAPI.create() }
}

fun writerRegistrationUtility(
    userId : Int, fullName : String, idCard : String, idCardPath : MultipartBody.Part,
    email : String, phone : String, address : String, bio : String,
    onResponse : (ResponseBody) -> Unit, onElse : (Response<ResponseBody>) -> Unit,
    onFailure : (Throwable) -> Unit
){
    WriterClient.instance.writerRegistration(
        userId.toRequestBody(),
        fullName.toRequestBody(),
        idCard.toRequestBody(),
        idCardPath,
        email.toRequestBody(),
        phone.toRequestBody(),
        address.toRequestBody(),
        bio.toRequestBody()
    ).enqueueCallback(onResponse,onElse,onFailure)
}

fun allPendingRegistrationUtility(
    onResponse: (List<Registration>) -> Unit, onElse: (Response<List<Registration>>) -> Unit,
    onFailure: (Throwable) -> Unit
){
    WriterClient.instance.allPendingRegistration().enqueueCallback(onResponse,onElse,onFailure)
}

fun getRegistrationUtility(
    registrationId : Int, onResponse: (Registration) -> Unit,
    onElse: (Response<Registration>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.getRegistration(registrationId)
        .enqueueCallback(onResponse,onElse,onFailure)
}

fun updateReviewedAtUtility(
    registrationId: Int, onResponse: (ResponseBody) -> Unit,
    onElse: (Response<ResponseBody>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.updateReviewedAt(registrationId).enqueueCallback(onResponse,onElse,onFailure)
}

fun rejectRegistrationUtility(
    registrationId: Int, onResponse: (ResponseBody) -> Unit,
    onElse: (Response<ResponseBody>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.rejectRegistration(registrationId).enqueueCallback(onResponse,onElse,onFailure)
}

fun approveRegistrationUtility(
    registrationId: Int,userId : Int, onResponse: (ResponseBody) -> Unit,
    onElse: (Response<ResponseBody>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.approveRegistration(registrationId,userId).enqueueCallback(onResponse,onElse,onFailure)
}

fun insertMagazineUtility(
    writerId : Int, title : String, description : String, coverPath : MultipartBody.Part,
    categoryId : Int, onResponse: (JsonObject) -> Unit,
    onElse: (Response<JsonObject>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.insertMagazine(
        writerId.toRequestBody(),
        title.toRequestBody(),
        description.toRequestBody(),
        coverPath,
        categoryId.toRequestBody()
    ).enqueueCallback(onResponse,onElse,onFailure)
}

fun insertArticleUtility(
    magazineId : Int, title : String, content : String,
    imagePath : MultipartBody.Part, onResponse: (JsonObject) -> Unit,
    onElse: (Response<JsonObject>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.insertArticle(
        magazineId.toRequestBody(),
        title.toRequestBody(),
        content.toRequestBody(),
        imagePath
    ).enqueueCallback(onResponse,onElse,onFailure)
}

fun getMagazineUtility(
    magazineId : Int, onResponse: (Magazine) -> Unit,
    onElse: (Response<Magazine>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.getMagazine(magazineId).enqueueCallback(onResponse,onElse,onFailure)
}

fun getAllMagazineUtility(
    onResponse: (List<Magazine>) -> Unit, onElse: (Response<List<Magazine>>) -> Unit,
    onFailure: (Throwable) -> Unit
){
    WriterClient.instance.getAllMagazine().enqueueCallback(onResponse,onElse,onFailure)
}

fun getCategoryUtility(
    categoryId : Int, onResponse: (Category) -> Unit,
    onElse: (Response<Category>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.getCategory(categoryId).enqueueCallback(onResponse,onElse,onFailure)
}

fun getAllCategoryUtility(
    onResponse: (List<Category>) -> Unit, onElse: (Response<List<Category>>) -> Unit,
    onFailure: (Throwable) -> Unit
){
    WriterClient.instance.getAllCategory().enqueueCallback(onResponse,onElse,onFailure)
}

fun getWriterUtility(
    writerId: Int, onResponse: (User) -> Unit,
    onElse: (Response<User>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.getWriter(writerId).enqueueCallback(onResponse,onElse,onFailure)
}

fun getArticleUtility(
    magazineId : Int, onResponse: (Article) -> Unit,
    onElse: (Response<Article>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.getArticle(magazineId).enqueueCallback(onResponse,onElse,onFailure)
}

fun banMagazineUtility(
    magazineId : Int, onResponse: (JsonObject) -> Unit,
    onElse: (Response<JsonObject>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.banMagazine(magazineId).enqueueCallback(onResponse,onElse,onFailure)
}

fun unbannedMagazineUtility(
    magazineId : Int, onResponse: (JsonObject) -> Unit,
    onElse: (Response<JsonObject>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.unbannedMagazine(magazineId).enqueueCallback(onResponse,onElse,onFailure)
}

fun getAllBannedMagazineUtility(
    onResponse: (List<Magazine>) -> Unit,
    onElse: (Response<List<Magazine>>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.getAllBannedMagazine().enqueueCallback(onResponse,onElse,onFailure)
}

fun myMagazineListUtility(
    writerId: Int, onResponse: (List<Magazine>) -> Unit,
    onElse: (Response<List<Magazine>>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.myMagazineList(writerId).enqueueCallback(onResponse,onElse,onFailure)
}

fun searchMagazineUtility(
    prompt : String? , onResponse: (List<Magazine>) -> Unit ,
    onElse: (Response<List<Magazine>>) -> Unit , onFailure: (Throwable) -> Unit
){
    WriterClient.instance.searchMagazine(prompt).enqueueCallback(onResponse,onElse,onFailure)
}

fun getFavoriteUtility(
    userId: Int, magazineId: Int, onResponse: (Favorite) -> Unit,
    onElse: (Response<Favorite>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.getFavorite(userId,magazineId).enqueueCallback(onResponse,onElse,onFailure)
}

fun insertFavoriteUtility(
    userId: Int, magazineId: Int, onResponse: (ResponseBody) -> Unit,
    onElse: (Response<ResponseBody>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.insertFavorite(userId,magazineId).enqueueCallback(onResponse,onElse,onFailure)
}

fun deleteFavoriteUtility(
    userId: Int, magazineId: Int, onResponse: (ResponseBody) -> Unit,
    onElse: (Response<ResponseBody>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.deleteFavorite(userId,magazineId).enqueueCallback(onResponse,onElse,onFailure)
}

fun myFavoriteUtility(
    userId: Int, onResponse: (List<Magazine>) -> Unit,
    onElse: (Response<List<Magazine>>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.myFavorite(userId).enqueueCallback(onResponse,onElse,onFailure)
}

fun getCategoryByNameUtility(
    name : String, onResponse: (Category) -> Unit,
    onElse: (Response<Category>) -> Unit, onFailure: (Throwable) -> Unit
){
    WriterClient.instance.getCategoryByName(name).enqueueCallback(onResponse,onElse,onFailure)
}

fun getMagazinesByCategoryUtility(
    categoryId: Int, onResponse: (List<Magazine>) -> Unit,
    onElse: (Response<List<Magazine>>) -> Unit, onFailure: (Throwable) -> Unit
){
   WriterClient.instance.getMagazinesByCategory(categoryId)
       .enqueueCallback(onResponse,onElse,onFailure)
}

private fun String.toRequestBody() = this.toRequestBody("text/plain".toMediaTypeOrNull())
private fun Int.toRequestBody() = this.toString().toRequestBody("text/plain".toMediaTypeOrNull())