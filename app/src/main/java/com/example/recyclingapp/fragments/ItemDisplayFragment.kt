package com.example.recyclingapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import coil.load
import com.example.recyclingapp.MainActivity
import com.example.recyclingapp.R
import com.example.recyclingapp.viewmodels.PackageViewModel
import org.w3c.dom.Text

class ItemDisplayFragment : Fragment(R.layout.item_display_layout) {


    private val mlogTag: String = "Item Display Fragment";

    /**
     * ViewModel!
     */
    val packageViewModel: PackageViewModel by activityViewModels {
        (requireActivity() as MainActivity).appViewModelFactory
    }

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
        var description: TextView = view.findViewById<TextView>(R.id.item_description);
        var name: TextView = view.findViewById<TextView>(R.id.item_name)
        var image: ImageView = view.findViewById<ImageView>(R.id.item_image)
        var isRecyclable: TextView = view.findViewById<TextView>(R.id.item_recyclable)

        /**
         * If there is some item selected, then the data should be filled in!
         */
        packageViewModel.selectedPackage.observe(viewLifecycleOwner){
            description.text = packageViewModel.selectedPackage.value?.description;
            name.text = packageViewModel.selectedPackage.value!!.name;  //Always a name
            isRecyclable.text = "Recyclable: " + packageViewModel.selectedPackage.value!!.recycling_pos.toString();
            if(packageViewModel.selectedPackage.value?.image_link != null) {    //Load the image using coil if present...
                image.load(packageViewModel.selectedPackage.value!!.image_link){
                    crossfade(true)
                    placeholder(R.drawable.recycle)
                    error(R.drawable.recycle)
                }
            }
        }

        /**
         * Listeners...
         */
        closeButton.setOnClickListener {    //Send back to home!
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                HomeFragment()).addToBackStack(null).commit();
            packageViewModel.clearSelectedPackage();
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