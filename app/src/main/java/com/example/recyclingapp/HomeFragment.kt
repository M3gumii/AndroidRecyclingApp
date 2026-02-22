package com.example.recyclingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class HomeFragment : Fragment(R.layout.home_fragment) {

    private val mlogTag: String = "Home Fragment";

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(mlogTag, "onCreateView called!");
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userNameBox = view.findViewById<TextView>(R.id.usernameBox);
        val userInfoBox = view.findViewById<TextView>(R.id.userInfo);
        val numItemsRecycledBox = view.findViewById<TextView>(R.id.num_items_scanned_text_box);

        //Todo - set boxes based on if the user logged in or not!
        //TODO - Set numItemsRecycledBox based on num items recycled!

        super.onViewCreated(view, savedInstanceState)
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