package com.example.recyclingapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.recyclingapp.MainActivity
import com.example.recyclingapp.R
import com.example.recyclingapp.viewmodels.UserViewModel
import kotlin.getValue
import kotlin.math.log

class AccountEditFragment: Fragment(R.layout.account_edit_fragment) {

    private val mlogTag: String = "Account Edit Fragment";

    /**
     * View model
     */
    private val userViewModel: UserViewModel by activityViewModels {
        (requireActivity() as MainActivity).appViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(mlogTag, "onCreateView called!");
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(mlogTag, "onViewCreated called!")

        val deleteAccountButton: Button = view.findViewById<Button>(R.id.delete_button)
        val logoutButton: Button = view.findViewById<Button>(R.id.logout_button)
        val closeButton: Button = view.findViewById<Button>(R.id.closeButton)

        deleteAccountButton.setOnClickListener {
            val user = userViewModel.selectedUser.value
            if(user != null) {
                userViewModel.deleteUser(user.username) //Delete the user from the db!
                userViewModel.clearUser()

                Toast.makeText(requireContext(), "User " + user.username + " has been deleted!", Toast.LENGTH_SHORT).show()
            }
        }

        logoutButton.setOnClickListener {
            val user = userViewModel.selectedUser.value
            if(user != null) {
                userViewModel.clearUser()
                Toast.makeText(requireContext(), "User " + user.username + " has logged out.", Toast.LENGTH_SHORT).show()
            }
        }

        closeButton.setOnClickListener {    //Send back to home!
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                HomeFragment()).addToBackStack(null).commit();
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