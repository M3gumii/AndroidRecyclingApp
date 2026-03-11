package com.example.recyclingapp.viewmodels.copilot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclingapp.dataClasses.copilot.CopilotRetrofitApi

/**
 * A factory that knows how to create ViewModels that need a CopilotRetrofitApi.
 *
 * Android normally can only create ViewModels with EMPTY constructors.
 * This factory provides the database to each ViewModel that requires it.
 *
 * If the ViewModel type is unknown, it throws an error.
 */
class CopilotAppViewModelFactory(
    private val api: CopilotRetrofitApi
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CopilotViewModel::class.java)) {
            return CopilotViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}