package com.example.recyclingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.recyclingapp.R
import com.example.recyclingapp.viewmodels.ai.AIViewModel
import androidx.fragment.app.viewModels
import com.example.recyclingapp.viewmodels.ai.AIAppViewModelFactory
import com.example.recyclingapp.BuildConfig
import com.example.recyclingapp.MainActivity
import com.example.recyclingapp.dataClasses.ai.AIContactor
import com.example.recyclingapp.viewmodels.database.PackageViewModel
import com.example.recyclingapp.viewmodels.database.PreviousSearchesViewModel
import com.example.recyclingapp.viewmodels.database.UserViewModel

class SubmissionFragment : Fragment() {
    private val packageViewModel: PackageViewModel by activityViewModels {
        (requireActivity() as MainActivity).appViewModelFactory
    }
    private val userViewModel: UserViewModel by activityViewModels {
        (requireActivity() as MainActivity).appViewModelFactory
    }
    private val previousSearchesViewModel: PreviousSearchesViewModel by activityViewModels {
        (requireActivity() as MainActivity).appViewModelFactory
    }
    private var barcode: String? = null
    private val aiViewModel: AIViewModel by viewModels {
        AIAppViewModelFactory(AIContactor.create(BuildConfig.AI_API_KEY))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get barcode passed in (optional)
        barcode = arguments?.getString("barcode")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate your submission_fragment.xml
        return inflater.inflate(R.layout.submission_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleText: TextView = view.findViewById(R.id.title_text)

        val barcodeBox: EditText = view.findViewById(R.id.barcode_text)
        val barcodeToAdd = arguments?.getString("barcode") ?: ""
        barcodeBox.setText(barcodeToAdd)

        val nameBox: EditText = view.findViewById(R.id.item_name_box)
        val brandBox: EditText = view.findViewById(R.id.brand_box)
        val descriptionBox: EditText = view.findViewById(R.id.description_box)
        val submitButton: Button = view.findViewById(R.id.submit_button)

        // Set title
        titleText.text = "Item Not Found"

        submitButton.setOnClickListener {
            val name = nameBox.text.toString()
            val brand = brandBox.text.toString()
            val description = descriptionBox.text.toString()

            if (name.isBlank() || brand.isBlank() || description.isBlank()) {
                Toast.makeText(requireContext(), "Please fill in all information.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // TODO: submit info to Gemini
            aiViewModel.submitManualPackage(barcodeToAdd, name, brand, description)

            aiViewModel.pkg.observe(viewLifecycleOwner) { pkg ->

                if (pkg != null) {

                    // Save to DB
                    packageViewModel.addPackage(
                        pkg.barcode,
                        pkg.name,
                        pkg.recycling_pos,
                        pkg.image_link,
                        pkg.description
                    )

                    Toast.makeText( requireContext(), "Item added successfully!", Toast.LENGTH_SHORT ).show()

                    // Update user history (same as camera fragment)
                    userViewModel.selectedUser.value?.let { user ->
                        previousSearchesViewModel.addSearch(
                            user.username,
                            pkg.barcode,
                            pkg.name
                        )

                        userViewModel.addToUserRecyclingCount(user.username)
                    }

                    // Navigate to display (same as camera)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ItemDisplayFragment())
                        .addToBackStack(null)
                        .commit()

                }
            }

            aiViewModel.error.observe(viewLifecycleOwner) { errorMsg ->
                if (errorMsg == "LOW_CONFIDENCE") {
                    Toast.makeText(requireContext(), "Saved, but AI was unsure. Please verify.", Toast.LENGTH_LONG).show()
                }
            }

        }
    }

}