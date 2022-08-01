package com.echoeyecodes.testapplication.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.echoeyecodes.testapplication.domain.repository.UserRepository
import com.echoeyecodes.testapplication.data.models.NetworkState
import com.echoeyecodes.testapplication.data.models.User
import com.echoeyecodes.testapplication.utils.AndroidUtilities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class SampleActivityViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    private val networkState = mutableStateOf<NetworkState?>(null)
    private val users = mutableStateOf<List<User>>(ArrayList())

    init {
        fetchData()
        userRepository.getUsersFlow().onEach {
            users.value = it
        }.launchIn(viewModelScope)
    }

    fun getNetworkState(): State<NetworkState?> {
        return networkState
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                networkState.value = NetworkState.Loading
                userRepository.fetchUsers()
                networkState.value = NetworkState.Complete
            } catch (exception: Exception) {
                AndroidUtilities.log(exception.message)
                if (exception is HttpException) {
                    networkState.value =
                        NetworkState.Error("Internal Server error")
                } else networkState.value =
                    NetworkState.Error(exception.message ?: "An unknown error has occurred")
            }
        }
    }

    fun getUsers(): State<List<User>> {
        return users
    }
}