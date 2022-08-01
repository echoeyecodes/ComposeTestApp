package com.echoeyecodes.testapplication.data.models

sealed class NetworkState {
    object Loading : NetworkState()
    class Error(val message: String) : NetworkState()
    object Complete : NetworkState()
}