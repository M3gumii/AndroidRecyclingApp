package com.example.recyclingapp.viewmodels.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclingapp.dataClasses.ai.AIRetrofitApi

/**
 * A factory that knows how to create ViewModels that need a CopilotRetrofitApi.
 *
 * Android normally can only create ViewModels with EMPTY constructors.
 * This factory provides the database to each ViewModel that requires it.
 *
 * If the ViewModel type is unknown, it throws an error.
 */
class AIAppViewModelFactory(
    private val api: AIRetrofitApi
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AIViewModel::class.java)) {
            return AIViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}