package com.example.recyclingapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclingapp.dataClasses.RecyclingDatabase
import com.example.recyclingapp.dataClasses.User
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Allows for the fragment to update the repo through this!
 */
class UserViewModel(private val repo: RecyclingDatabase) : ViewModel() {

    //Needed for comparison.
    var attemptedUsername: String? = null
    var attemptedPassword: String? = null
    var attemptedEmail: String? = null

    private val _addUserResult = MutableLiveData<Int>()
    val addUserResult: LiveData<Int> = _addUserResult

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

    fun loadUser() {
        attemptedUsername?.let {    //Run if username not null
            viewModelScope.launch {
                _selectedUser.value = repo.getUser(attemptedUsername!!) //!! forces to non null. If not crashes prog.
                Log.d("UserViewModel", "user gained!" + _selectedUser.value?.username)
            }
        }
    }

    fun clearUser(){
        _selectedUser.value = null;
    }

    /**
     * 1 == username faulty
     * 2 == email faulty
     * -1 == user added
     */
    fun addUser(username: String, password: String, email: String) {
        viewModelScope.launch {
            //Check if the user already exists with that username or email...
            val existingUser = repo.getUser(username)
            val existingEmail = repo.getUserByEmail(email)

            when {
                existingUser != null -> _addUserResult.value = 1    //Username used
                existingEmail != null -> _addUserResult.value = 2   //email used...
                else -> {   //Free to add!
                    val newUser = User(username, password, email)   //we can add in the new user!
                    repo.addUser(newUser)
                    _selectedUser.value = newUser
                    _addUserResult.value = 0
                }
            }
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