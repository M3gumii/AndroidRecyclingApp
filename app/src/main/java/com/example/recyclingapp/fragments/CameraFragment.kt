package com.example.recyclingapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recyclingapp.R
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager
import androidx.fragment.app.activityViewModels
import com.example.recyclingapp.MainActivity
import com.example.recyclingapp.viewmodels.database.PackageViewModel
import com.example.recyclingapp.viewmodels.database.PreviousSearchesViewModel
import com.example.recyclingapp.viewmodels.database.UserViewModel
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.AlertDialog
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.widget.TextView

class CameraFragment : Fragment(R.layout.camera_fragment) {
    private val mlogTag: String = "Camera Fragment";

    /**
     * View models
     */
    private val userViewModel: UserViewModel by activityViewModels {
        (requireActivity() as MainActivity).appViewModelFactory
    }
    private val packageViewModel: PackageViewModel by activityViewModels {
        (requireActivity() as MainActivity).appViewModelFactory
    }
    private val previousSearchesViewModel: PreviousSearchesViewModel by activityViewModels {
        (requireActivity() as MainActivity).appViewModelFactory
    }

    private lateinit var previewView: PreviewView
    private val cameraExecutor = Executors.newSingleThreadExecutor()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(mlogTag, "onCreateView called!");
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @androidx.camera.core.ExperimentalGetImage
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(mlogTag, "onViewCreated called!")

        previewView = view.findViewById(R.id.previewView)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 1001)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(mlogTag, "onStart called!")
    }

    override fun onResume() {
        super.onResume()
        Log.d(mlogTag, "onResume called!")
    }

    override fun onPause() {
        super.onPause()
        Log.d(mlogTag, "onPause called!")
    }

    override fun onStop() {
        super.onStop()
        Log.d(mlogTag, "onStop called!")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(mlogTag, "onDestroyView called!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(mlogTag, "onDestroy called!")
        cameraExecutor.shutdown()
    }

    @androidx.camera.core.ExperimentalGetImage
    private fun startCamera() {

        Log.d(mlogTag, "Starting camera...")

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({

            val cameraProvider = cameraProviderFuture.get()

            // Preview use case
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            // Barcode scanner options (UPC only)
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    com.google.mlkit.vision.barcode.common.Barcode.FORMAT_UPC_A,
                    com.google.mlkit.vision.barcode.common.Barcode.FORMAT_UPC_E
                )
                .build()

            val scanner = BarcodeScanning.getClient(options)

            // Image analysis use case
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalyzer.setAnalyzer(cameraExecutor) { imageProxy ->

                val mediaImage = imageProxy.image

                if (mediaImage != null) {

                    val image = InputImage.fromMediaImage(
                        mediaImage,
                        imageProxy.imageInfo.rotationDegrees
                    )

                    scanner.process(image)
                        .addOnSuccessListener { barcodes ->

                            for (barcode in barcodes) {

                                val upc = barcode.rawValue  //Get the 12 dig num for the barcode!
                                if (upc != null) {
                                    Log.d(mlogTag, "UPC Found: $upc")

                                    // TODO: Query Supabase database here
                                    packageViewModel.getPackage(upc);
                                    if (packageViewModel.selectedPackage.value == null) {
                                        //Package match not available! -> Need to add the package to the db!

                                        requireActivity().supportFragmentManager.beginTransaction()
                                            .replace(
                                                R.id.fragment_container,
                                                ItemNotFoundFragment()
                                            ).addToBackStack(null).commit();


                                    } else {  //Package match found! Send to the item info screen!

                                        if (userViewModel.selectedUser.value != null) {   //add to user searches if logged in
                                            packageViewModel.getPackage(upc)    //Get from the db!

                                            previousSearchesViewModel.addSearch(
                                                userViewModel.selectedUser.value!!.username,
                                                upc,
                                                packageViewModel.selectedPackage.value!!.name
                                            )
                                            userViewModel.addToUserRecyclingCount(userViewModel.selectedUser.value!!.username)
                                        }

                                        requireActivity().supportFragmentManager.beginTransaction()
                                            .replace(
                                                R.id.fragment_container,
                                                ItemDisplayFragment()
                                            ).addToBackStack(null).commit();

                                    }

                                    imageAnalyzer.clearAnalyzer() // Stop scanning after first match


                                    // TODO: Query Supabase database here
                                    // Placeholder for DB check (always false for now)
                                    val foundInDatabase = false

                                    if (!foundInDatabase) {
                                        callUpcApi(upc!!)
                                    }

                                    break
                                }
                            }
                        }
                        .addOnFailureListener {
                            Log.e(mlogTag, "Barcode scanning failed", it)
                        }
                        .addOnCompleteListener {
                            imageProxy.close()
                        }

                } else {
                    imageProxy.close()
                }
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                viewLifecycleOwner,
                cameraSelector,
                preview,
                imageAnalyzer
            )

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    // Permissions
    private fun allPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun callUpcApi(upc: String) {

        Log.d(mlogTag, "Calling UPCitemdb API for $upc")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.upcitemdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(
            com.example.recyclingapp.network.UpcApiService::class.java
        )

        service.lookupUpc(upc).enqueue(object : Callback<com.example.recyclingapp.network.UpcResponse> {

            override fun onResponse(
                call: Call<com.example.recyclingapp.network.UpcResponse>,
                response: Response<com.example.recyclingapp.network.UpcResponse>
            ) {

                if (response.isSuccessful && response.body()?.items?.isNotEmpty() == true) {

                    val item = response.body()!!.items[0]

                    val title = item.title ?: "Unknown"
                    val brand = item.brand ?: "Unknown"
                    val upc = item.upc ?: "Unknown"
                    val weight = item.weight ?: "Unknown"
                    val category = item.category ?: "Unknown"
                    val imageUrl = item.images?.firstOrNull { it.startsWith("https") }

                    Log.d("IMAGE_DEBUG", "Image URL: $imageUrl")

                    showConfirmationDialog(title, brand, upc, imageUrl)

                } else {
                    Log.d(mlogTag, "UPC not found in API")
                    navigateToNotFound(upc)
                }
            }

            override fun onFailure(call: Call<com.example.recyclingapp.network.UpcResponse>, t: Throwable) {
                Log.e(mlogTag, "API call failed", t)
            }
        })
    }

    private fun showConfirmationDialog(
        title: String,
        brand: String,
        upc: String,
        imageUrl: String?
    ) {

        val dialogView = layoutInflater.inflate(
            R.layout.dialog_item_confirmation,
            null
        )

        val imageView = dialogView.findViewById<ImageView>(R.id.productImage)
        val titleView = dialogView.findViewById<TextView>(R.id.productTitle)
        val brandView = dialogView.findViewById<TextView>(R.id.productBrand)
        val upcView = dialogView.findViewById<TextView>(R.id.productUpc)

        titleView.text = title
        brandView.text = "Brand: $brand"
        upcView.text = "UPC: $upc"

        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(imageUrl)
                .into(imageView)
        } else {
            imageView.visibility = View.GONE
        }

        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Yes") { _, _ ->
                // TODO: Later save to Supabase
            }

            .setNegativeButton("No") { _, _ ->
                navigateToNotFound(upc)
            }

            .show()
    }

    private fun navigateToNotFound(upc: String) {

        val fragment = ItemNotFoundFragment()

        val bundle = Bundle()
        bundle.putString("UPC", upc)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}