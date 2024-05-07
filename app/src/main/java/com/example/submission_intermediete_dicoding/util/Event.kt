package com.example.submission_intermediete_dicoding.util

open class Event<out String> (private val content : String) {
    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled() : String? {
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }

    fun peekContent() : String = content
    fun isNotEmpty(): Boolean = true
}