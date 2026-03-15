package com.example.recyclingapp.viewmodels.copilot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclingapp.dataClasses.ai.AIPrompter
import com.example.recyclingapp.dataClasses.ai.AIRetrofitApi
import com.example.recyclingapp.dataClasses.database.Package
import kotlinx.coroutines.launch

/**
 * Used to prompt AI to find food package descriptions, etc. based on the barcode
 * entered.
 *
 * Follows the AIRetrofitApi
 */
class CopilotViewModel(private val api: AIRetrofitApi): ViewModel() {

    private val copilot = AIPrompter()
    private val _pkg = MutableLiveData<Package>()
    val pkg: LiveData<Package> = _pkg

    /**
     * Updates the Live Data with the package data found.
     */
    fun scan(barcode: String) {
        viewModelScope.launch {
            val data = copilot.lookupBarcode(barcode, api)
            val pkgFound = Package(
                barcode = barcode,
                name = data.getString("name"),
                recycling_pos = data.getBoolean("recycling_pos"),
                image_link = null,
                description = data.getString("description"),
                verified = false
            )

            _pkg.value = pkgFound
        }
    }
}