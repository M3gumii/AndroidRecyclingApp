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
import com.example.recyclingapp.R
import com.example.recyclingapp.viewmodels.database.UserViewModel
import androidx.fragment.app.activityViewModels
import com.example.recyclingapp.MainActivity

class LoginFragment : Fragment(R.layout.login_fragment) {

    private val mlogTag: String = "Login Fragment";

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
        Log.d(mlogTag, "onViewCreated called!");

        val loginButton: Button = view.findViewById<Button>(R.id.loginButton);
        val skipButton: Button = view.findViewById<Button>(R.id.skipButton);
        val userBox : EditText = view.findViewById<EditText>(R.id.usernameBox);
        val passBox : EditText = view.findViewById<EditText>(R.id.passwordBox);
        val createAccountButton: Button = view.findViewById<Button>(R.id.createAccountButton);


        //Make sure the fragment observes the viewModel!
        userViewModel.selectedUser.observe(viewLifecycleOwner) { user ->
            Log.d(mlogTag, user?.username.toString())
            //If selected user changes, check if valid!
            if (user != null) { //Check for validity!
                Log.d(mlogTag, "User loaded: $user")
                if(userViewModel.attemptedPassword.equals(user.password)){
                    Log.d(mlogTag, "USR FOUND: " + user.username);
                    //send to home screen as user was entered!
                    requireActivity().supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container,
                        HomeFragment()).addToBackStack(null).commit();
                }else{  //Notify user of invalid login.
                    Toast.makeText(requireContext(), "Invalid user/pass.\nPlease try again", Toast.LENGTH_SHORT).show();
                    userViewModel.clearUser();  //Non match for password...
                }
            }
        }

        loginButton.setOnClickListener{
            Log.d(mlogTag, "LOGIN CLICKED!")
            userViewModel.attemptedUsername = userBox.text.toString();
            userViewModel.attemptedPassword = passBox.text.toString();
            userViewModel.loadUser();    //Will set off our observer!
        }

        skipButton.setOnClickListener {
            Log.d(mlogTag, "SKIP CLICKED!")
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                HomeFragment()).addToBackStack(null).commit();
        }

        createAccountButton.setOnClickListener {
            Log.d(mlogTag, "CREATE ACCOUNT CLICKED!")
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
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