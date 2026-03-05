package com.example.recyclingapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.recyclingapp.R

class ItemDisplayFragment : Fragment(R.layout.item_display_layout) {


    private val mlogTag: String = "Item Display Fragment";

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

        val closeButton: Button = view.findViewById<Button>(R.id.closeButton);


        /**
         * Listeners...
         */
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