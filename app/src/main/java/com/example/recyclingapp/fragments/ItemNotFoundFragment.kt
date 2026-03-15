package com.example.recyclingapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.recyclingapp.MainActivity
import com.example.recyclingapp.R
import com.example.recyclingapp.dataClasses.ai.AIContactor
import com.example.recyclingapp.viewmodels.ai.AIAppViewModelFactory
import com.example.recyclingapp.viewmodels.ai.AIViewModel
import com.example.recyclingapp.viewmodels.database.PackageViewModel
import com.example.recyclingapp.viewmodels.database.PreviousSearchesViewModel
import com.example.recyclingapp.viewmodels.database.UserViewModel
import com.example.recyclingapp.BuildConfig

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
    private val AIViewModel: AIViewModel by viewModels {
        AIAppViewModelFactory(AIContactor.create(BuildConfig.AI_API_KEY))
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

        val loadingText: TextView = view.findViewById(R.id.loading_text)

        val barcodeToAdd = packageViewModel.getAttemptedBarcode()

        if (barcodeToAdd == null) {
            Toast.makeText(requireContext(), "INVALID BARCODE ENTERED!", Toast.LENGTH_SHORT).show()
            return
        }

        // Only scan ONCE
        if (AIViewModel.pkg.value == null) {
            Log.d(mlogTag, "Scanning barcode!")
            AIViewModel.scan(barcodeToAdd)
        }

        // Observe results
        AIViewModel.pkg.observe(viewLifecycleOwner) { pkgFound ->

            Log.d(mlogTag, "PACKAGE FOUND RESP $pkgFound")

            if (pkgFound != null) {
                packageViewModel.addPackage(
                    pkgFound.barcode,
                    pkgFound.name,
                    pkgFound.recycling_pos,
                    pkgFound.image_link,
                    pkgFound.description
                )

                userViewModel.selectedUser.value?.let { user ->
                    userViewModel.addToUserRecyclingCount(user.username)
                    previousSearchesViewModel.addSearch(user.username, barcodeToAdd, pkgFound.name)
                }
            } else {
                Toast.makeText(requireContext(), "BARCODE NOT FOUND!", Toast.LENGTH_LONG).show()
            }
        }

        // Navigation observer
        packageViewModel.selectedPackage.observe(viewLifecycleOwner) { pkg ->
            if (pkg != null) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ItemDisplayFragment())
                    .addToBackStack(null)
                    .commit()
            } else {
                loadingText.text = "UNABLE TO ADD THE ITEM!\nPLEASE NAVIGATE TO HOME AND TRY AGAIN LATER"
            }
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