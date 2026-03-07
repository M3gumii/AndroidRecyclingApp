package com.example.recyclingapp
import com.example.recyclingapp.viewmodels.AppViewModelFactory
import androidx.activity.viewModels
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclingapp.fragments.CameraFragment
import com.example.recyclingapp.fragments.HomeFragment
import com.example.recyclingapp.fragments.LoginFragment
import com.example.recyclingapp.fragments.RecentFragment
import com.example.recyclingapp.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recyclingapp.dataClasses.RecyclingDatabase
import com.example.recyclingapp.dataClasses.SupabaseConnection
import com.example.recyclingapp.viewmodels.PackageViewModel
import com.example.recyclingapp.viewmodels.PreviousSearchesViewModel
import com.example.recyclingapp.viewmodels.UserViewModel

class MainActivity : AppCompatActivity() {  //AppCompatActivity to hold fragments...
    //Use fragmentActivity for fragments!
    private val mlogTag: String = "Main Activity";

    //Connect via the api connection we made in our singleton!
    private val repo: RecyclingDatabase = RecyclingDatabase(SupabaseConnection.api);
    //Creates the var only once when needed later. (by lazy)
    val appViewModelFactory by lazy { AppViewModelFactory(repo) }

    /**
     * VIEW MODELS
     */
    //Uses the custome viewModelFactory! -> Tells to use what db instance as a param!
    private val userViewModel: UserViewModel by viewModels { appViewModelFactory }  //Returns the userViewModel w/ the db constructor!
    private val packageViewModel: PackageViewModel by viewModels{ appViewModelFactory }
    private val previousSearchViewModel: PreviousSearchesViewModel by viewModels { appViewModelFactory }

    /**
     * Activity lifecycle methods
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(mlogTag, "onCreate called!");
        setContentView(R.layout.activity_main)  //Set the main layout via activity_main...

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_selection_options)
        ViewCompat.setOnApplyWindowInsetsListener(bottomNav) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                view.paddingLeft,
                view.paddingTop,
                view.paddingRight,
                systemBars.bottom
            )
            insets
        }

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
        botNavView.setOnItemSelectedListener{ selection ->
            when(selection.itemId){ //Use when (java if p much) to select what to swap to!
                R.id.home_select_button -> {    //Send to home screen!
                    System.out.println("HOME SELECTED!!")
                    currentFragment = fm.findFragmentById(R.id.fragment_container);
                    if(currentFragment !is HomeFragment){
                        //Use addToBackStack to allow the user to return to the previous fragment...
                        fm.beginTransaction().replace(R.id.fragment_container, HomeFragment()).addToBackStack(null).commit();
                    }
                }
                R.id.search_select_button -> {
                    //Send to search screen
                    System.out.println("SEARCH SELECTED!!");
                    currentFragment = fm.findFragmentById(R.id.fragment_container);
                    if(currentFragment !is SearchFragment){
                        //Use addToBackStack to allow the user to return to the previous fragment...
                        fm.beginTransaction().replace(R.id.fragment_container, SearchFragment()).addToBackStack(null).commit();
                    }
                }
                R.id.camera_select_button -> {
                    //send to camera scanning screen
                    System.out.println("CAMERA SELECTED!!");
                    currentFragment = fm.findFragmentById(R.id.fragment_container);
                    if(currentFragment !is CameraFragment){
                        //Use addToBackStack to allow the user to return to the previous fragment...
                        fm.beginTransaction().replace(R.id.fragment_container, CameraFragment()).addToBackStack(null).commit();
                    }

                }
                R.id.recent_select_button -> {

                    System.out.println("RECENTS SELECTED!!");
                    currentFragment = fm.findFragmentById(R.id.fragment_container);
                    if(currentFragment !is RecentFragment){
                        //Use addToBackStack to allow the user to return to the previous fragment...
                        fm.beginTransaction().replace(R.id.fragment_container, RecentFragment()).addToBackStack(null).commit();
                    }
                }
            }
            true
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