package com.trios2025dj.jetpackcomposeexample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trios2025dj.jetpackcomposeexample.datastore.UserPreferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class User(val first: String, val last: String)

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = UserPreferences(application)

    private val _user = MutableStateFlow(User("", ""))
    val user: StateFlow<User> = _user

    init {
        viewModelScope.launch {
            prefs.firstName.collect { first ->
                prefs.lastName.collect { last ->
                    _user.value = User(first, last)
                }
            }
        }
    }

    fun saveUser(first: String, last: String) {
        viewModelScope.launch {
            prefs.saveFirstName(first)
            prefs.saveLastName(last)
        }
    }
}
