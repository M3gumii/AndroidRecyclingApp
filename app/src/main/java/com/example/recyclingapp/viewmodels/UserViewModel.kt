package com.example.recyclingapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclingapp.database.RecyclingDatabase
import com.example.recyclingapp.database.User
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Allows for the fragment to update the repo through this!
 */
class UserViewModel(private val repo: RecyclingDatabase) : ViewModel() {

    /**
     * All users
     */
    private val _users = MutableLiveData<List<User>>(emptyList())
    val users: LiveData<List<User>> = _users

    /**
     * Single user
     */
    private val _selectedUser = MutableLiveData<User?>()
    val selectedUser: LiveData<User?> = _selectedUser

    fun getUsers() {
        viewModelScope.launch {
            _users.value = repo.getAllUsers()
        }
    }

    fun loadUser(username: String) {
        viewModelScope.launch {
            _selectedUser.value = repo.getUser(username)
            Log.d("UserViewModel", "user gained!" + _selectedUser.toString())
        }
    }

    fun clearUser(){
        _selectedUser.value = null;
    }

    fun addUser(username: String, password: String, email: String) {
        viewModelScope.launch {
            val user = User(username, password, email)
            repo.addUser(user)
            _users.value = repo.getAllUsers()
        }
    }

    fun deleteUser(username: String) {
        viewModelScope.launch {
            repo.deleteUser(username)
            _users.value = repo.getAllUsers()
        }
    }

    fun addToUserRecyclingCount(username: String) {
        viewModelScope.launch {
            repo.updateUserCount(username)
            _users.value = repo.getAllUsers()
        }
    }
}