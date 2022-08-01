package com.echoeyecodes.testapplication.domain.di

import com.echoeyecodes.testapplication.data.repository.UserRepositoryImpl
import com.echoeyecodes.testapplication.domain.repository.UserRepository
import com.echoeyecodes.testapplication.domain.service.UserDao
import com.echoeyecodes.testapplication.domain.service.UserService
import com.echoeyecodes.testapplication.utils.ApiClient
import com.echoeyecodes.testapplication.utils.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun provideUserDao(): UserDao{
        return AppDatabase.getInstance().userDao()
    }

    @Provides
    fun provideUserService():UserService{
        return ApiClient.getInstance().getClient(UserService::class.java)
    }

    @Provides
    fun provideUserRepository(userService: UserService, userDao: UserDao):UserRepository{
        return UserRepositoryImpl(userService, userDao)
    }

}