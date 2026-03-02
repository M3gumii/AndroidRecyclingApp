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
import com.example.recyclingapp.viewmodels.PackageViewModel
import com.example.recyclingapp.viewmodels.PreviousSearchesViewModel
import com.example.recyclingapp.viewmodels.UserViewModel
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors
import kotlin.getValue

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
                                if (upc != null){
                                    Log.d(mlogTag, "UPC Found: $upc")

                                    // TODO: Query Supabase database here
                                    packageViewModel.getPackage(upc);
                                    if(packageViewModel.selectedPackage.value == null){
                                        //Package match not available!


                                    }else{  //Package match found! Send to the item info screen!

                                        if(userViewModel.selectedUser.value != null){   //add to user searches if logged in
                                            previousSearchesViewModel.addSearch(userViewModel.selectedUser.value!!.username, upc)
                                            userViewModel.addToUserRecyclingCount(userViewModel.selectedUser.value!!.username)
                                        }

                                        requireActivity().supportFragmentManager.beginTransaction().replace(
                                            R.id.fragment_container,
                                            ItemDisplayFragment()).addToBackStack(null).commit();

                                    }

                                }

                                imageAnalyzer.clearAnalyzer() // Stop scanning after first match
                                break
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
}