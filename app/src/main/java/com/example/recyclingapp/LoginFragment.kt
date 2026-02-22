package com.example.recyclingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class LoginFragment : Fragment(R.layout.login_fragment) {

    private val mlogTag: String = "Login Fragment";

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
        Log.d(mlogTag, "onViewCreated called!");

        val loginButton: Button = view.findViewById<Button>(R.id.loginButton);
        val skipButton: Button = view.findViewById<Button>(R.id.skipButton);
        val userBox : EditText = view.findViewById<EditText>(R.id.usernameBox);
        val passBox : EditText = view.findViewById<EditText>(R.id.passwordBox);
        val createAccountButton: Button = view.findViewById<Button>(R.id.createAccountButton);

        loginButton.setOnClickListener{
            Log.d(mlogTag, "LOGIN CLICKED!")
            //TODO - confirm user and pass ok!
        }
        skipButton.setOnClickListener {
            Log.d(mlogTag, "SKIP CLICKED!")
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                HomeFragment()).addToBackStack(null).commit();
        }
        createAccountButton.setOnClickListener {
            Log.d(mlogTag, "CREATE ACCOUNT CLICKED!")
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                AccountCreationFragment()).commit();
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