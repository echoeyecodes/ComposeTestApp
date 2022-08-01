package com.echoeyecodes.testapplication.domain.repository

import androidx.lifecycle.LiveData
import com.echoeyecodes.testapplication.data.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsersFlow(): Flow<List<User>>
    suspend fun fetchUsers()
}