package moi.next.usercard.Service

import moi.next.usercard.Model.User
import retrofit2.Call
import retrofit2.http.GET

interface JsonPlaceholderApi {

    @GET("users")
    suspend fun getUsers(): List<User>
}