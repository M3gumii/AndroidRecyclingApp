package com.example.recyclingapp.viewmodels.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclingapp.dataClasses.database.RecyclingDatabase
import com.example.recyclingapp.dataClasses.database.Package
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Allows for the fragment to update the repo through this!
 */
class PackageViewModel(private val repo: RecyclingDatabase) : ViewModel() {

    /**
     * Get packages!
     */
    private val _packages = MutableLiveData<List<Package>>(emptyList())
    val packages: LiveData<List<Package>> = _packages

    /**
     * Single package to show in fragment
     */
    private val _selectedPackage = MutableLiveData<Package?>()
    val selectedPackage: LiveData<Package?> = _selectedPackage

    private var attemptedBarcode: String? = null;

    fun getPackages() {
        viewModelScope.launch {
            _packages.value = repo.getAllPackages()
        }
    }

    fun addPackage(
        barcode: String,
        name: String,
        recycling_pos: Boolean = false,
        image_link: String? = null,
        description: String? = null
    ) {
        viewModelScope.launch {
            val newPackage = Package(
                barcode = barcode,
                name = name,
                recycling_pos = recycling_pos,
                image_link = image_link,
                description = description
            )

            repo.addPackage(newPackage)
            _packages.value = repo.getAllPackages()
        }
    }

    fun deletePackage(barcode: String) {
        viewModelScope.launch {
            repo.deletePackage(barcode)
            _packages.value = repo.getAllPackages()
        }
    }

    fun clearSelectedPackage(){
        viewModelScope.launch {
            _selectedPackage.value = null;
        }
    }

    fun getPackage(barcode: String){
        viewModelScope.launch {
            attemptedBarcode = barcode;
            _selectedPackage.value = repo.getPackage(barcode);
        }
    }

    fun getAttemptedBarcode(): String?{
        return attemptedBarcode;
    }
}