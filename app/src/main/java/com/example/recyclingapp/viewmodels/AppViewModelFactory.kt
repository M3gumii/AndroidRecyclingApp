package com.example.recyclingapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclingapp.database.RecyclingDatabase

/**
 * A factory that knows how to create ViewModels that need a RecyclingDatabase.
 *
 * Android normally can only create ViewModels with EMPTY constructors.
 * This factory provides the database to each ViewModel that requires it.
 *
 * When a ViewModel is requested, this class checks which one it is,
 * (UserViewModel, PackageViewModel, or PreviousSearchViewModel),
 * and returns a new instance with the given repo.
 *
 * If the ViewModel type is unknown, it throws an error.
 */
class AppViewModelFactory(
    private val repo: RecyclingDatabase
) : ViewModelProvider.Factory { //Returns a factory.

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) ->   //if this model, return constructor with repo!
                UserViewModel(repo) as T

            modelClass.isAssignableFrom(PackageViewModel::class.java) ->
                PackageViewModel(repo) as T

            modelClass.isAssignableFrom(PreviousSearchesViewModel::class.java) ->
                PreviousSearchesViewModel(repo) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
}