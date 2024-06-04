package com.example.submission_intermediete_dicoding.util

import com.example.submission_intermediete_dicoding.data.response.AddStoryResponse
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.data.response.LoginResponse
import com.example.submission_intermediete_dicoding.data.response.LoginResult
import com.example.submission_intermediete_dicoding.data.response.RegisterResponse
import com.example.submission_intermediete_dicoding.data.response.StoryResponse

object DummyData {
    fun generateDummyStories(): StoryResponse {
        val listStory = ArrayList<ListStoryItem>()
        for (i in 1..20) {
            val story = ListStoryItem(
                id = "id_$i",
                description = "Deksripsi ke $i",
                name = "Name $i",
                createdAt = "2024-06-04T15:06:32.839Z",
                lat = i.toDouble() * 10,
                lon = i.toDouble() * 10,
                photoUrl = "https://images.unsplash.com/photo-1715521801494-3e72f7c9361b?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            )
            listStory.add(story)
        }

        return StoryResponse(
            error = false,
            message = "Stories fetched successfully",
            listStory = listStory
        )
    }

    fun generateDummyCreateStory(): AddStoryResponse {
        return AddStoryResponse(
            error = false,
            message = "success"
        )
    }

    fun generateDummyRegister(): RegisterResponse {
        return RegisterResponse(
            error = false,
            message = "User Created"
        )
    }

    fun generateDummyLogin(): LoginResponse {
        return LoginResponse(
            error = false,
            message = "success",
            loginResult = LoginResult(
                userId = "user-_mGuiPsaawejrpAb",
                name = "nabil",
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLV9tR3VpUHNhYXdlanJwQWIiLCJpYXQiOjE3MTc1MTM4MjN9.LnsZ9ggMgp7qMHurLHeqFe0mDtWwGdqX3MOovKoUJjE"
            )
        )
    }
}