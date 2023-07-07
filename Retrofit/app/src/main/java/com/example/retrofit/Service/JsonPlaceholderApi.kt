package com.example.retrofit.Service

import com.example.retrofit.Model.User
import retrofit2.Call
import retrofit2.http.GET

interface JsonPlaceholderApi {

    @GET("users")
    fun getUsers(): Call<List<User>>
}