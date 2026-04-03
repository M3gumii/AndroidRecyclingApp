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

    /**
     * @requires - the barcode to add is NOT already in the db!
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var aiFinished = false
        super.onViewCreated(view, savedInstanceState)
        Log.d(mlogTag, "OnViewCreated called!")

        val loadingText: TextView = view.findViewById(R.id.loading_text)
        loadingText.text = "Please wait.\nThe item is being added to the app now!"

        val barcodeToAdd = packageViewModel.getAttemptedBarcode()
        Log.d(mlogTag, "ATTEMPTED BARCODE: $barcodeToAdd");

        if (barcodeToAdd == null) {
            Toast.makeText(requireContext(), "INVALID BARCODE ENTERED!", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d(mlogTag, "Scanning barcode!")
        AIViewModel.scan(barcodeToAdd)

        // Observe results
        AIViewModel.pkg.observe(viewLifecycleOwner) { pkgFound ->
            //If a package is found, then it should change this!

            Log.d(mlogTag, "PACKAGE FOUND RESP $pkgFound")
            aiFinished = true

            if (pkgFound != null) { //Add the new package to the db!
                packageViewModel.addPackage(
                    pkgFound.barcode,
                    pkgFound.name,
                    pkgFound.recycling_pos,
                    pkgFound.image_link,
                    pkgFound.description
                )

                userViewModel.selectedUser.value?.let { user -> //If the user is logged in, add it to their searches!
                    userViewModel.addToUserRecyclingCount(user.username)
                    previousSearchesViewModel.addSearch(user.username, barcodeToAdd, pkgFound.name)
                }
            } else {
                if (AIViewModel.error.value == null) {
                    loadingText.text = "Item not found in our database.\nPlease try another item."
                }
            }
        }

        // Navigation observer
        packageViewModel.selectedPackage.observe(viewLifecycleOwner) { pkg ->
            //If the package changed to the new item found...

            if (pkg != null) {  //Go to the display page!
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ItemDisplayFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        AIViewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            if (errorMsg != null) {
                loadingText.text = errorMsg
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