package com.example.recyclingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.recyclingapp.R

class SubmissionFragment : Fragment() {

    private var barcode: String? = null

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
                // Could also show a Toast
                return@setOnClickListener
            }

            // TODO: handle submitting this info to Gemini/your backend
            // e.g., call a ViewModel function here

        }
    }
}