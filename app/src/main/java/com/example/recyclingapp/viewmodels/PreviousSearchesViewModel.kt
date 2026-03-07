package com.example.recyclingapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclingapp.dataClasses.RecyclingDatabase
import com.example.recyclingapp.dataClasses.PreviousSearch
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Allows for the fragment to update the repo through this!
 *
 * Previous searches specific to the user!
 */
class PreviousSearchesViewModel(private val repo: RecyclingDatabase) : ViewModel() {

    /**
     * Get previous searches!
     */
    private val _searches = MutableLiveData<List<PreviousSearch>>(emptyList())
    val searches: LiveData<List<PreviousSearch>> = _searches

    /**
     * Single search for display
     */
    private val _selectedSearch = MutableLiveData<PreviousSearch?>()
    val selectedSearch: LiveData<PreviousSearch?> = _selectedSearch

    fun getSearchesByUsername(username: String) {
        viewModelScope.launch {
            _searches.value = repo.getSearchesByUsername(username)
        }
    }

    fun getAllSearches() {
        viewModelScope.launch {
            _searches.value = repo.getAllSearches()
        }
    }

    fun loadSearch(username: String, barcode: String) {
        viewModelScope.launch {
            _selectedSearch.value = repo.getSpecificSearch(username, barcode)
        }
    }

    fun addSearch(username: String, barcode: String, name: String) {
        viewModelScope.launch {
            val newSearch = PreviousSearch(username, barcode, name)
            repo.addSearch(newSearch)
            _searches.value = repo.getSearchesByUsername(username)
        }
    }

    fun deleteSearch(username: String, barcode: String) {
        viewModelScope.launch {
            repo.deleteSearch(barcode, username)
            _searches.value = repo.getSearchesByUsername(username)
        }
    }
}