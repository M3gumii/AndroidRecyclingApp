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

class AccountCreationFragment : Fragment(R.layout.account_creation_fragment) {
    private val mlogTag: String = "Account Creation Fragment";

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

        val submitButton: Button = view.findViewById<Button>(R.id.submit_button);
        val usernameBox: EditText = view.findViewById<EditText>(R.id.username_box);
        val passwordBox: EditText = view.findViewById<EditText>(R.id.password_box);
        val emailBox: EditText = view.findViewById<EditText>(R.id.email_box);

        userViewModel.addUserResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                0 -> {
                    Toast.makeText(requireContext(), "Added user!", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container,
                        HomeFragment()).addToBackStack(null).commit();
                }
                1 -> Toast.makeText(requireContext(), "Username already in use.", Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(requireContext(), "Email already in use.", Toast.LENGTH_SHORT).show()
            }
        }

        submitButton.setOnClickListener {
            val username = usernameBox.text.toString()
            val password = passwordBox.text.toString()
            val email = emailBox.text.toString()
            if(!username.isBlank() && !password.isBlank() && !email.isBlank()){
                userViewModel.attemptedUsername = username; //Check for username used...
                userViewModel.attemptedPassword = password;
                userViewModel.attemptedEmail = email;
                userViewModel.addUser(username, password, email)
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