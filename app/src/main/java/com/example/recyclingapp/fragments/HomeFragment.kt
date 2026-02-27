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
import com.example.recyclingapp.R

class HomeFragment : Fragment(R.layout.home_fragment) {

    private val mlogTag: String = "Home Fragment";

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
        Log.d(mlogTag, "onViewCreated called!");

        //User info
        val userNameBox = view.findViewById<TextView>(R.id.usernameBox);
        val userInfoBox = view.findViewById<TextView>(R.id.userInfo);
        val numItemsRecycledBox = view.findViewById<TextView>(R.id.num_items_scanned_text_box);

        //Links
        val columbusRecyclingLink: Button = view.findViewById<Button>(R.id.columbus_recycling_button_link);
        val rumpkeRecyclingLink: Button = view.findViewById<Button>(R.id.rumpke_recycling_button_link);

        //Todo - set boxes based on if the user logged in or not!
        //TODO - Set numItemsRecycledBox based on num items recycled!

        columbusRecyclingLink.setOnClickListener {
            //brings up window by the link!
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(columbus_recycling_url));
            startActivity(intent);
        }
        rumpkeRecyclingLink.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(rumpke_recycling_url)));
        }

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