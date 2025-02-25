package com.src.electronicmagazine.api

import com.src.electronicmagazine.data.Registration
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import java.sql.Timestamp

interface WriterAPI {

    @Multipart
    @POST("writer-registration")
    fun writerRegistration(
        @Part("user_id") userId : RequestBody,
        @Part("fullname") fullName : RequestBody,
        @Part("id_card") idCard : RequestBody,
        @Part idCardPath : MultipartBody.Part,
        @Part("email") email : RequestBody,
        @Part("phone") phone : RequestBody,
        @Part("address") address : RequestBody,
        @Part("bio") bio : RequestBody,
    ) : Call<ResponseBody>

    @GET("all-pending-registration")
    fun allPendingRegistration() : Call<List<Registration>>

    @GET("get-registration/{registration_id}")
    fun getRegistration(
        @Path("registration_id") registrationId : Int
    ) : Call<Registration>

    @PUT("update-reviewed-at/{registration_id}")
    fun updateReviewedAt(
        @Path("registration_id") registrationId: Int,
    ) : Call<ResponseBody>

    @PUT("reject-registration/{registration_id}")
    fun rejectRegistration(
        @Path("registration_id") registrationId : Int
    ) : Call<ResponseBody>

    @PUT("approve-registration/{registration_id}/{user_id}")
    fun approveRegistration(
        @Path("registration_id") registrationId: Int,
        @Path("user_id") userId : Int
    ) : Call<ResponseBody>

    companion object{
        fun create() : WriterAPI{
            val writerClient : WriterAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WriterAPI::class.java)

            return writerClient
        }
    }
}