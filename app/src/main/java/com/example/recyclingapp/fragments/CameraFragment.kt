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

class CameraFragment : Fragment(R.layout.camera_fragment) {
    private val mlogTag: String = "Camera Fragment";
    private var hasScanned = false

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
        hasScanned = false
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

            // Image analysis use case
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalyzer.setAnalyzer(cameraExecutor) { imageProxy ->
                if (hasScanned) {
                    imageProxy.close()
                    return@setAnalyzer
                }

                val mediaImage = imageProxy.image

                if (mediaImage != null) {

                    val image = InputImage.fromMediaImage(
                        mediaImage,
                        imageProxy.imageInfo.rotationDegrees
                    )

                    /**
                     * NOW GET THE BARCODE!
                     */

                    scanner.process(image)
                        .addOnSuccessListener { barcodes ->

                            for (barcode in barcodes) {

                                val upc = barcode.rawValue  //Get the 12 dig num for the barcode!
                                if (upc != null && !hasScanned) {
                                    hasScanned = true
                                    Log.d(mlogTag, "UPC Found: $upc")

                                    packageViewModel.getPackage(upc)

                                    packageViewModel.isLoading.observe(viewLifecycleOwner) { loading ->

                                        if (loading == false) {  // ONLY act AFTER DB finishes

                                            val pkg = packageViewModel.selectedPackage.value

                                            if (pkg == null) {
                                                // NOT FOUND
                                                imageAnalyzer.clearAnalyzer()
                                                navigateToNotFound(upc)

                                            } else {
                                                // FOUND
                                                imageAnalyzer.clearAnalyzer()

                                                if (userViewModel.selectedUser.value != null) {
                                                    previousSearchesViewModel.addSearch(
                                                        userViewModel.selectedUser.value!!.username,
                                                        upc,
                                                        pkg.name
                                                    )
                                                    userViewModel.addToUserRecyclingCount(
                                                        userViewModel.selectedUser.value!!.username
                                                    )
                                                }

                                                requireActivity().supportFragmentManager.beginTransaction()
                                                    .replace(R.id.fragment_container, ItemDisplayFragment())
                                                    .addToBackStack(null)
                                                    .commit()
                                            }

                                            imageAnalyzer.clearAnalyzer()
                                        }
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

    fun <T> androidx.lifecycle.LiveData<T>.observeOnce(
        owner: androidx.lifecycle.LifecycleOwner,
        observer: (T) -> Unit
    ) {
        observe(owner, object : androidx.lifecycle.Observer<T> {
            override fun onChanged(t: T) {
                observer(t)
                removeObserver(this)
            }
        })
    }

    private val scanner by lazy {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                com.google.mlkit.vision.barcode.common.Barcode.FORMAT_UPC_A,
                com.google.mlkit.vision.barcode.common.Barcode.FORMAT_UPC_E
            )
            .build()

        BarcodeScanning.getClient(options)
    }
}