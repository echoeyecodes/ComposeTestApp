package com.echoeyecodes.testapplication.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(@PrimaryKey val id: String, val name: String, val avatar: String, val age: Long, val department: String, val profileId: String)