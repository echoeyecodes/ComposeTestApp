package com.echoeyecodes.testapplication.utils

import com.echoeyecodes.testapplication.data.models.User


fun User.generateId(): String {
    val subjectChar = this.department[0].uppercaseChar()
    return "NN/${subjectChar}19/$id"
}