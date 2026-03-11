package com.example.recyclingapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.recyclingapp.MainActivity
import com.example.recyclingapp.R
import com.example.recyclingapp.viewmodels.PackageViewModel
import com.example.recyclingapp.viewmodels.PreviousSearchesViewModel
import com.example.recyclingapp.viewmodels.UserViewModel
import kotlin.getValue

class ItemNotFoundFragment : Fragment() {
    private val mlogTag: String = "Item Not Found Fragment";

    /**
     * View models
     */
    private val userViewModel: UserViewModel by activityViewModels {
        (requireActivity() as MainActivity).appViewModelFactory
    }
    private val packageViewModel: PackageViewModel by activityViewModels {
        (requireActivity() as MainActivity).appViewModelFactory
    }
    private val previousSearchesViewModel: PreviousSearchesViewModel by activityViewModels {
        (requireActivity() as MainActivity).appViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.item_not_found_fragment,
            container,
            false
        )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(mlogTag, "OnViewCreated called!")

        /**
         *  Adds the barcode to the database!
         *
         */

        val barcodeToAdd = packageViewModel.attemptedBarcode;

        /**
         * Prompt Gemini to get the item desc. and whether
         * recyclable, etc.
         */

        /**
         * Add in the scan to the user!
         */
        if(userViewModel.selectedUser.value != null && barcodeToAdd != null) {
            userViewModel.addToUserRecyclingCount(userViewModel.selectedUser.value!!.username)
//            previousSearchesViewModel.addSearch(userViewModel.selectedUser.value!!.username, barcodeToAdd, "Add this")  //TODO fin this
        }


    }

    override fun onDestroyView(){
        super.onDestroyView()
        Log.d(mlogTag, "onDestroyView called!");
    }

    override fun onStart(){
        super.onStart()
        Log.d(mlogTag, "onStart called!");
    }

    override fun onResume(){
        super.onResume()
        Log.d(mlogTag, "onResume called!");
    }

    override fun onPause(){
        super.onPause();
        Log.d(mlogTag, "onPause called!");
    }

    override fun onStop(){
        super.onStop();
        Log.d(mlogTag, "onStop called!");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(mlogTag, "onDestroy called!");
    }
}