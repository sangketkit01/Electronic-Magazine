package com.src.electronicmagazine.api_util

import com.src.electronicmagazine.api.UserAPI
import com.src.electronicmagazine.data.User
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


object UserClient{
    val instance : UserAPI by lazy { UserAPI.create() }
}

fun <T> Call<T>.enqueueCallback(
    onResponse: (T) -> Unit,
    onElse: (Response<T>) -> Unit,
    onFailure: (Throwable) -> Unit
) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            response.body()?.let(onResponse) ?: onElse(response)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            onFailure(t)
        }
    })
}

fun registerUtility(
    username : String , email : String,
    password : String , onResponse: (ResponseBody) -> Unit,
    onElse: (Response<ResponseBody>) -> Unit , onFailure: (Throwable) -> Unit
){
    UserClient.instance.register(username,email,password).enqueueCallback(onResponse,onElse,onFailure)
}

fun loginUtility(
    username : String, password : String, onResponse: (User) -> Unit,
    onElse: (Response<User>) -> Unit, onFailure: (Throwable) -> Unit
){
    UserClient.instance.login(username,password).enqueueCallback(onResponse,onElse,onFailure)
}

fun updateProfileUtility(
    userId: Int, username: String, email: String, name: String, imagePath: MultipartBody.Part?,
    onResponse: (ResponseBody) -> Unit, onElse: (Response<ResponseBody>) -> Unit,
    onFailure: (Throwable) -> Unit
){
    val usernameBody = username.toRequestBody()
    val emailBody = email.toRequestBody()
    val nameBody = name.toRequestBody()

    UserClient.instance.updateProfile(userId,usernameBody,emailBody,nameBody,imagePath)
        .enqueueCallback(onResponse,onElse,onFailure)
}

private fun String.toRequestBody() = this.toRequestBody("text/plain".toMediaTypeOrNull())
private fun Int.toRequestBody() = this.toString().toRequestBody("text/plain".toMediaTypeOrNull())