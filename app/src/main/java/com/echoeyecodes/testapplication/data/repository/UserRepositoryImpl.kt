package com.echoeyecodes.testapplication.data.repository

import com.echoeyecodes.testapplication.domain.repository.UserRepository
import com.echoeyecodes.testapplication.domain.service.UserDao
import com.echoeyecodes.testapplication.domain.service.UserService
import com.echoeyecodes.testapplication.data.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UserRepositoryImpl(private val userService: UserService, private val userDao: UserDao) :
    UserRepository {

    override fun getUsersFlow(): Flow<List<User>> {
        return userDao.getUsersLiveData()
    }

    override suspend fun fetchUsers() {
        withContext(Dispatchers.IO) {
            val users = userService.fetchUsers()
            userDao.insertUsers(users)
        }
    }
}