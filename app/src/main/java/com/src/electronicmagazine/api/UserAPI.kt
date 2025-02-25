package com.src.electronicmagazine.api

import com.src.electronicmagazine.data.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface UserAPI {

    @FormUrlEncoded
    @POST("register-account")
    fun register(
        @Field("username") username : String,
        @Field("email") email : String,
        @Field("password") password : String,
    ) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username : String,
        @Field("password") password: String
    ) : Call<User>

    @Multipart
    @PUT("update-profile/{user_id}")
    fun updateProfile(
        @Path("user_id") userId: Int,
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody,
        @Part("name") name: RequestBody,
        @Part profilePath: MultipartBody.Part?
    ) : Call<ResponseBody>

    companion object{
        fun create() : UserAPI{
            val userClient : UserAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserAPI::class.java)

            return userClient
        }
    }
}