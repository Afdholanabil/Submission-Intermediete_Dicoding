package com.example.submission_intermediete_dicoding.data.retrofit

import com.example.submission_intermediete_dicoding.data.response.AddStoryResponse
import com.example.submission_intermediete_dicoding.data.response.LoginResponse
import com.example.submission_intermediete_dicoding.data.response.RegisterResponse
import com.example.submission_intermediete_dicoding.data.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun postRegister (
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin (
        @Field("email") email : String,
        @Field("password") password: String
    ) : Call<LoginResponse>

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
    ): StoryResponse


    @GET("stories")
    suspend fun getStoryWithLoc(
        @Header("Authorization") token: String,
        @Query("location") location: Int ,
    ) : StoryResponse

    @GET("stories")
    suspend fun getStoryPaging(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ) : StoryResponse

    @Multipart
    @POST("stories")
    suspend fun addStory (
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody?,
        @Part photo: MultipartBody.Part?,
        @Part("lat") lat: Double?,
        @Part("lon") lon: Double?
    ) : Response<AddStoryResponse>
}