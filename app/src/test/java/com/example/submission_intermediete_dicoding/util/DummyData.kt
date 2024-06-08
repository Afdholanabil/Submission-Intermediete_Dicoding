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
                createdAt = "2024-01-22T22:22:22Z",
                description = "Description $i",
                id = "id_$i",
                lat = i.toDouble() * 10,
                lon = i.toDouble() * 10,
                name = "Name $i",
                photoUrl = "https://blog.eigeradventure.com/wp-content/uploads/2022/07/tips-foto-aesthetics-2.jpg"
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