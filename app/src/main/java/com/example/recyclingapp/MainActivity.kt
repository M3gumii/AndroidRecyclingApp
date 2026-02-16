package com.example.recyclingapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {  //AppCompatActivity to hold fragments...
    //Use fragmentActivity for fragments!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)  //Set the main layout via activity_main...

        //Fragment manager to set screens!
        val fm = supportFragmentManager;
        var currentFragment: Fragment?;

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
                    currentFragment = fm.findFragmentById(R.id.home_fragment);
                    if(currentFragment == null){
                        currentFragment = CameraFragment();
                        fm.beginTransaction().add(R.id.fragment_container, HomeFragment()).commit();
                    }
                }
                R.id.search_select_button -> {
                    //Send to search screen
                }
                R.id.camera_select_button -> {
                    //send to camera scanning screen
                    System.out.println("CAMERA SELECTED!!")
                    currentFragment = fm.findFragmentById(R.id.camera_fragment);
                    if(currentFragment == null){
                        currentFragment = CameraFragment();
                        fm.beginTransaction().add(R.id.fragment_container, CameraFragment()).commit();
                    }
                }
                R.id.recent_select_button -> {

                }
            }

        }

    }
}