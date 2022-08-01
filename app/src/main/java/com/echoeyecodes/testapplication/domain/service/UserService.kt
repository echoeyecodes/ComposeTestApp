package com.echoeyecodes.testapplication.domain.service

import com.echoeyecodes.testapplication.data.models.User
import retrofit2.http.GET

interface UserService {
    @GET("api/v1/students")
    suspend fun fetchUsers():List<User>
}