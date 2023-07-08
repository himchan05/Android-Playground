package moi.next.usercard.Service

import moi.next.usercard.Model.User

class UserRepository {
    private val userService = RetrofitInstance.userService
    suspend fun getUsers(): List<User> {
        return userService.getUsers()
    }
}