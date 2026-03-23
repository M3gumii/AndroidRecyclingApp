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
import androidx.fragment.app.activityViewModels
import com.example.recyclingapp.MainActivity
import com.example.recyclingapp.R
import com.example.recyclingapp.viewmodels.database.UserViewModel
import kotlin.getValue

class HomeFragment : Fragment(R.layout.home_fragment) {

    private val mlogTag: String = "Home Fragment";

    /**
     * View model
     */
    private val userViewModel: UserViewModel by activityViewModels {
        (requireActivity() as MainActivity).appViewModelFactory
    }

    val columbus_recycling_url = "https://www.columbus.gov/Services/Trash-Recycling-Bulk-Collection/Recycling";
    val rumpke_recycling_url = "https://www.rumpke.com/about-us/service-areas/oh/franklin-county/columbus/recycling-resource-center";

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

        //User info
        val userNameBox = view.findViewById<TextView>(R.id.usernameBox);
        val userInfoBox = view.findViewById<TextView>(R.id.userInfo);
        val numItemsRecycledBox = view.findViewById<TextView>(R.id.num_items_scanned_text_box);
        val loginOrEditButton = view.findViewById<Button>(R.id.loginOrEditButton)

        //Links
        val columbusRecyclingLink: Button = view.findViewById<Button>(R.id.columbus_recycling_button_link);
        val rumpkeRecyclingLink: Button = view.findViewById<Button>(R.id.rumpke_recycling_button_link);

        columbusRecyclingLink.setOnClickListener {
            //brings up window by the link!
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(columbus_recycling_url));
            startActivity(intent);
        }
        rumpkeRecyclingLink.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(rumpke_recycling_url)));
        }

        if(userViewModel.selectedUser.value != null){
            userNameBox.text = userViewModel.selectedUser.value!!.username;
            userInfoBox.text = "Welcome " + userViewModel.selectedUser.value!!.username + "!\nLet's recycle together!!!";
        }

        //Whenever user info changes, these should auto be updated!
        userViewModel.selectedUser.observe(viewLifecycleOwner){ user ->
            if(user != null){
                numItemsRecycledBox.text = user.num_items_recycled.toString();
                loginOrEditButton.setText("Edit Account")
            }else{
                loginOrEditButton.setText("Log in/Create Account")
            }
        }

        loginOrEditButton.setOnClickListener {
            if(userViewModel.selectedUser.value != null){   //user logged in!
                requireActivity().supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    AccountEditFragment()).addToBackStack(null).commit();
            }else{  //send to log in screen!
                requireActivity().supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    LoginFragment()).addToBackStack(null).commit();
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