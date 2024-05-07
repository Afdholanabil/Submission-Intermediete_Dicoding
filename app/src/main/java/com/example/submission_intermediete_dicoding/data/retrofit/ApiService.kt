package com.example.submission_intermediete_dicoding.data.retrofit

import com.example.submission_intermediete_dicoding.data.response.LoginResponse
import com.example.submission_intermediete_dicoding.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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

}