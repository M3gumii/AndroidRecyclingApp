package com.example.recyclingapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclingapp.MainActivity
import com.example.recyclingapp.R
import com.example.recyclingapp.dataClasses.RecentItemAdapter
import com.example.recyclingapp.viewmodels.PackageViewModel
import com.example.recyclingapp.viewmodels.PreviousSearchesViewModel
import com.example.recyclingapp.viewmodels.UserViewModel
import java.util.Collections.list
import java.util.concurrent.locks.ReentrantLock
import kotlin.getValue

class RecentFragment : Fragment(R.layout.recent_fragment) {


    private val mlogTag: String = "Recents Fragment";

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
    lateinit var adapter: RecentItemAdapter

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

        val closeButton: Button = view.findViewById<Button>(R.id.closeButton)
        val userNotLoggedInLayout: LinearLayout = view.findViewById<LinearLayout>(R.id.logged_out_text_box)

        //Set up the adapter to init to an empty list...
        adapter = RecentItemAdapter(emptyList()) { recentClicked -> //On the clicking of an item, send to the item screen!
            packageViewModel.getPackage(recentClicked.barcode)  //Set the package to the clicked item!

            //send to package view screen
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                ItemDisplayFragment()).addToBackStack(null).commit();
        }

        //Set up the recycler view
        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.recent_item_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        userViewModel.selectedUser.observe(viewLifecycleOwner){ user ->
            if (user != null){
                userNotLoggedInLayout.visibility = View.GONE
                previousSearchesViewModel.getSearchesByUsername(user.username)
                previousSearchesViewModel.searches.observe(viewLifecycleOwner){ items ->
                    adapter.updateItems(items)
                }
            }else{
                //No user present!
                userNotLoggedInLayout.visibility = View.VISIBLE
            }
        }

        closeButton.setOnClickListener {
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