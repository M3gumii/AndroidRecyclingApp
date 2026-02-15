package com.example.recyclingapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {  //AppCompatActivity to hold fragments...
    //Use fragmentActivity for fragments!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)  //Set the main layout via activity_main...

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
                R.id.home_select_button -> {
                    //Send to home screen
                    System.out.println("HOME SELECTED!!")
                }
                R.id.search_select_button -> {
                    //Send to search screen
                }
                R.id.camera_select_button -> {
                    //send to camera scanning screen
                }
            }

        }

    }
}