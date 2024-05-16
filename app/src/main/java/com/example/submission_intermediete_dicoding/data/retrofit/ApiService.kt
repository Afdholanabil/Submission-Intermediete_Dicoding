package com.example.submission_intermediete_dicoding.data.retrofit

import android.net.Uri
import com.example.submission_intermediete_dicoding.data.response.AddStoryResponse
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.data.response.LoginResponse
import com.example.submission_intermediete_dicoding.data.response.RegisterResponse
import com.example.submission_intermediete_dicoding.data.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
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

    @Multipart
    @POST("stories")
    fun addStory (
        @Header("Authorization") token: String,
        @Part("description") description: String?,
        @Part photo: MultipartBody.Part?,
        @Part("lat") lat: Double?,
        @Part("lon") lon: Double?
    ) : Call<AddStoryResponse>
}