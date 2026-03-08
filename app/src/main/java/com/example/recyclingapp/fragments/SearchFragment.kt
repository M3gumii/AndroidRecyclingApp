package com.example.recyclingapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclingapp.MainActivity
import com.example.recyclingapp.R
import com.example.recyclingapp.dataClasses.RecentItemAdapter
import com.example.recyclingapp.dataClasses.SearchAdapter
import com.example.recyclingapp.viewmodels.PackageViewModel
import com.example.recyclingapp.viewmodels.PreviousSearchesViewModel
import com.example.recyclingapp.viewmodels.UserViewModel
import kotlin.getValue

class SearchFragment : Fragment(R.layout.search_fragment) {


    private val mlogTag: String = "Search Fragment";

    /**
     * View model
     */
    private val userViewModel: UserViewModel by activityViewModels {
        (requireActivity() as MainActivity).appViewModelFactory
    }
    private val previousSearchesViewModel: PreviousSearchesViewModel by activityViewModels {
        (requireActivity() as MainActivity).appViewModelFactory
    }
    private val packageViewModel: PackageViewModel by activityViewModels {
        (requireActivity() as MainActivity).appViewModelFactory
    }

    /**
     * Adapter used to fill in selectable recent items...
     */
    lateinit var adapter: SearchAdapter

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

        val search_item_name: EditText = view.findViewById<EditText>(R.id.search_name_box)
        val close_button: Button = view.findViewById<Button>(R.id.closeButton)

        //Set up the adapter to init to an empty list...
        adapter = SearchAdapter(emptyList()) { searchSelected -> //On the clicking of an item, send to the item screen!
            packageViewModel.getPackage(searchSelected.barcode)  //Set the package to the clicked item!

            if (userViewModel.selectedUser.value != null) { //On user selection, this counts as a search by the user!
                previousSearchesViewModel.addSearch(
                    userViewModel.selectedUser.value!!.username,
                    searchSelected.barcode,
                    searchSelected.name
                )
            }

            //send to package view screen
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                ItemDisplayFragment()).addToBackStack(null).commit();
        }

        //set up the recycler.
        val recycler: RecyclerView = view.findViewById<RecyclerView>(R.id.possible_search_items)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        search_item_name.doOnTextChanged { text, _, _, _ -> //As user types stuff in, bring up pos items!
            val currentText = text.toString().lowercase()
            packageViewModel.getPackages()

            val listOfItemMatches = packageViewModel.packages.value?.filter { item ->
                item.name.lowercase().startsWith(currentText)
            }   //All items that match so far!

            if(listOfItemMatches != null) { //If we had any matches, update the list!
                adapter.updateItems(listOfItemMatches)
            }
        }

        close_button.setOnClickListener {
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