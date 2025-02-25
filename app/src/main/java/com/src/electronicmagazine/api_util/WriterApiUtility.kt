package com.src.electronicmagazine.api_util

import com.src.electronicmagazine.api.UserAPI
import com.src.electronicmagazine.api.WriterAPI
import com.src.electronicmagazine.data.Registration
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

private fun String.toRequestBody() = this.toRequestBody("text/plain".toMediaTypeOrNull())
private fun Int.toRequestBody() = this.toString().toRequestBody("text/plain".toMediaTypeOrNull())