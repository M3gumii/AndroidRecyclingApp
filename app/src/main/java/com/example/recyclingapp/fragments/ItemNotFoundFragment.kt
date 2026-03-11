package com.example.recyclingapp.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.recyclingapp.MainActivity
import com.example.recyclingapp.R
import com.example.recyclingapp.dataClasses.copilot.CopilotContactor
import com.example.recyclingapp.dataClasses.copilot.CopilotRetrofitApi
import com.example.recyclingapp.viewmodels.copilot.CopilotAppViewModelFactory
import com.example.recyclingapp.viewmodels.copilot.CopilotViewModel
import com.example.recyclingapp.viewmodels.database.PackageViewModel
import com.example.recyclingapp.viewmodels.database.PreviousSearchesViewModel
import com.example.recyclingapp.viewmodels.database.UserViewModel
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
    private val copilotViewModel: CopilotViewModel by viewModels {
        CopilotAppViewModelFactory(CopilotContactor.create("YOUR_API_KEY"))
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
        if(barcodeToAdd != null) {
            copilotViewModel.scan(barcodeToAdd)
            val pkgFound = copilotViewModel.pkg.value

            /**
             * Add in the scan for the user!
             */
            if(pkgFound != null){
                packageViewModel.addPackage(pkgFound.barcode, pkgFound.name,
                    pkgFound.recycling_pos, pkgFound.image_link, pkgFound.description)
                if (userViewModel.selectedUser.value != null) { //User logged in! Add to their count!
                    userViewModel.addToUserRecyclingCount(userViewModel.selectedUser.value!!.username)
                    previousSearchesViewModel.addSearch(userViewModel.selectedUser.value!!.username, barcodeToAdd, pkgFound.name)
                }
            }

        }else{
            Toast.makeText(requireContext(), "INVALID BARCODE ENTERED!", Toast.LENGTH_SHORT).show()
        }

        packageViewModel.selectedPackage.observe(viewLifecycleOwner){
            //On a package found, send to the package page!
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                ItemDisplayFragment()).addToBackStack(null).commit();
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