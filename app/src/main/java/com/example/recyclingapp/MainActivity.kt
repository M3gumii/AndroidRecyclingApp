package com.example.recyclingapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.log

class MainActivity : AppCompatActivity() {  //AppCompatActivity to hold fragments...
    //Use fragmentActivity for fragments!
    private val mlogTag: String = "Main Activity";

    /**
     * Activity lifecycle methods
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d(mlogTag, "onCreate called!");
        setContentView(R.layout.activity_main)  //Set the main layout via activity_main...

        //Fragment manager to set screens!
        val fm = supportFragmentManager;
        var currentFragment = fm.findFragmentById(R.id.fragment_container);
        //Start with login screen... Check if there's a fragment IN the container.
        if(currentFragment == null){
            fm.beginTransaction().add(R.id.fragment_container, LoginFragment()).commit();
        }

        /**
         * COMPONENTS
         */
        var botNavView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_selection_options);


        /**
         * LISTENERS
         */

        //Sets up the bottom button selection options to switch screens...
        botNavView.setOnItemReselectedListener{ selection ->
            when(selection.itemId){ //Use when (java if p much) to select what to swap to!
                R.id.home_select_button -> {    //Send to home screen!
                    System.out.println("HOME SELECTED!!")
                    currentFragment = fm.findFragmentById(R.id.fragment_container);
                    if(currentFragment !is HomeFragment ){
                        //Use addToBackStack to allow the user to return to the previous fragment...
                        fm.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit();
                    }
                }
                R.id.search_select_button -> {
                    //Send to search screen
                    System.out.println("SEARCH SELECTED!!");
                    currentFragment = fm.findFragmentById(R.id.fragment_container);
                    if(currentFragment !is SearchFragment ){
                        //Use addToBackStack to allow the user to return to the previous fragment...
                        fm.beginTransaction().replace(R.id.fragment_container, SearchFragment()).commit();
                    }
                }
                R.id.camera_select_button -> {
                    //send to camera scanning screen
                    System.out.println("CAMERA SELECTED!!");
                    currentFragment = fm.findFragmentById(R.id.fragment_container);
                    if(currentFragment !is CameraFragment ){
                        //Use addToBackStack to allow the user to return to the previous fragment...
                        fm.beginTransaction().replace(R.id.fragment_container, CameraFragment()).commit();
                    }

                }
                R.id.recent_select_button -> {

                    System.out.println("RECENTS SELECTED!!");
                    currentFragment = fm.findFragmentById(R.id.fragment_container);
                    if(currentFragment !is RecentFragment ){
                        //Use addToBackStack to allow the user to return to the previous fragment...
                        fm.beginTransaction().replace(R.id.fragment_container, RecentFragment()).commit();
                    }
                }
            }

        }

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