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
import com.example.recyclingapp.R
class ItemNotFoundFragment : Fragment() {

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

        val upc = arguments?.getString("UPC")

        val upcText = view.findViewById<TextView>(R.id.upcText)
        val submitButton = view.findViewById<Button>(R.id.submitProductButton)
        val goBackButton = view.findViewById<Button>(R.id.goBackButton)

        upcText.text = "UPC: $upc"

        goBackButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        submitButton.setOnClickListener {
            navigateToSubmitNewItem(upc)
        }

        return view
    }

    private fun navigateToSubmitNewItem(upc: String?) {

        val fragment = SubmitNewItemFragment()

        val bundle = Bundle()
        bundle.putString("UPC", upc)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}