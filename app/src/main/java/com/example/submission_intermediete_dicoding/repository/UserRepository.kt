package com.example.submission_intermediete_dicoding.repository

import com.example.submission_intermediete_dicoding.data.response.LoginResponse
import com.example.submission_intermediete_dicoding.data.response.RegisterResponse
import com.example.submission_intermediete_dicoding.data.retrofit.ApiService

class UserRepository(private val apiService: ApiService) {
    fun login(email: String, password: String): LoginResponse {
    val response = apiService.postLogin(email, password).execute()
    return if (response.isSuccessful) {
        response.body() ?: throw Exception("Login failed")
    } else {
        throw Exception("Login failed")
    }
}

    fun register(name: String, email: String, password: String): RegisterResponse {
        val response = apiService.postRegister(name, email, password).execute()
        return if (response.isSuccessful) {
            response.body() ?: throw Exception("Registration failed")
        } else {
            throw Exception("Registration failed")
        }
    }
}