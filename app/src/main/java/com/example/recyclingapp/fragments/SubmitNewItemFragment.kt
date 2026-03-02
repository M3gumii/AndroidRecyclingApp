package com.example.recyclingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.recyclingapp.R

class SubmitNewItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.submit_new_item_fragment,
            container,
            false
        )

        val assignButton = view.findViewById<Button>(R.id.assignProductButton)
        val searchButton = view.findViewById<Button>(R.id.searchDatabaseButton)
        val photoButton = view.findViewById<Button>(R.id.submitPhotoButton)
        val askTeamButton = view.findViewById<Button>(R.id.askTeamButton)

        assignButton.setOnClickListener {
            navigateToAssignProduct()
        }

        searchButton.setOnClickListener {
            navigateToSearchFragment()
        }

        photoButton.setOnClickListener {
            navigateToSubmitPhoto()
        }

        askTeamButton.setOnClickListener {
            navigateToAskTeam()
        }

        return view
    }

    private fun navigateToSearchFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SearchFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToAssignProduct() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AssignProductFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToSubmitPhoto() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SubmitPhotoFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToAskTeam() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AskTeamFragment())
            .addToBackStack(null)
            .commit()
    }
}