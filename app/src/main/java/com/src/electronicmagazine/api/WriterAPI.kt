package com.src.electronicmagazine.api

import com.google.gson.JsonObject
import com.src.electronicmagazine.data.Article
import com.src.electronicmagazine.data.Category
import com.src.electronicmagazine.data.Favorite
import com.src.electronicmagazine.data.Magazine
import com.src.electronicmagazine.data.Registration
import com.src.electronicmagazine.data.User
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

    @GET("get-writer/{writer_id}")
    fun getWriter(
        @Path("writer_id") writerId : Int
    ) : Call<User>

    @Multipart
    @POST("insert-magazine")
    fun insertMagazine(
        @Part("writer_id") writerId : RequestBody,
        @Part("title") title : RequestBody,
        @Part("description") description : RequestBody,
        @Part coverPath : MultipartBody.Part,
        @Part("category_id") categoryID : RequestBody
    ) : Call<JsonObject>

    @Multipart
    @POST("insert-article")
    fun insertArticle(
        @Part("magazine_id") magazineId : RequestBody,
        @Part("title") title : RequestBody,
        @Part("content") content : RequestBody,
        @Part imagePath : MultipartBody.Part
    ) : Call<JsonObject>

    @GET("get-magazine/{magazine_id}")
    fun getMagazine(
        @Path("magazine_id") magazineId : Int
    ) : Call<Magazine>

    @GET("get-all-magazine")
    fun getAllMagazine() : Call<List<Magazine>>

    @GET("get-category/{category_id}")
    fun getCategory(
        @Path("category_id") categoryId : Int
    ) : Call<Category>

    @GET("get-all-category")
    fun getAllCategory() : Call<List<Category>>

    @GET("get-article/{magazine_id}")
    fun getArticle(
        @Path("magazine_id") magazineId : Int
    ) : Call<Article>

    @PUT("ban-magazine/{magazine_id}")
    fun banMagazine(
        @Path("magazine_id") magazineId : Int
    ) : Call<JsonObject>

    @PUT("unbanned-magazine/{magazine_id}")
    fun unbannedMagazine(
        @Path("magazine_id") magazineId : Int
    ) : Call<JsonObject>

    @GET("get-all-banned-magazine")
    fun getAllBannedMagazine() : Call<List<Magazine>>

    @GET("my-magazine-list/{writer_id}")
    fun myMagazineList(
        @Path("writer_id") writerId: Int
    ) : Call<List<Magazine>>

    @GET("search-magazine/{prompt}")
    fun searchMagazine(
        @Path("prompt") prompt : String?
    ) : Call<List<Magazine>>

    @GET("get-favorite/{user_id}/{magazine_id}")
    fun getFavorite(
        @Path("user_id") userId : Int,
        @Path("magazine_id") magazineId: Int
    ) : Call<Favorite>

    @POST("insert-favorite/{user_id}/{magazine_id}")
    fun insertFavorite(
        @Path("user_id") userId: Int,
        @Path("magazine_id") magazineId: Int
    ) : Call<ResponseBody>

    @POST("delete-favorite/{user_id}/{magazine_id}")
    fun deleteFavorite(
        @Path("user_id") userId: Int,
        @Path("magazine_id") magazineId: Int
    ) : Call<ResponseBody>

    @GET("my-favorite/{user_id}")
    fun myFavorite(
        @Path("user_id") userId: Int
    ) : Call<List<Magazine>>

    @GET("get-category-by-name/{name}")
    fun getCategoryByName(
        @Path("name") name : String
    ) : Call<Category>

    @GET("get-magazines-by-category/{category_id}")
    fun getMagazinesByCategory(
        @Path("category_id") categoryId: Int
    ) : Call<List<Magazine>>

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