package com.meazza.instagram.ui.add_post.from

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.meazza.instagram.R
import com.meazza.instagram.ui.add_post.AddPostContainerDirections
import com.meazza.instagram.util.FILENAME_FORMAT
import kotlinx.android.synthetic.main.fragment_photo.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class PhotoFragment : Fragment(R.layout.fragment_photo) {

    private val mOutputDirectory by lazy { getOutputDirectory() }
    private var imageCapture: ImageCapture? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCamera()
        setUiAction()
    }

    private fun setUiAction() {
        iv_capture.setOnClickListener { takePhoto() }
    }

    private fun startCamera() {
        val cameraProviderFuture =
            ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()

            val cameraSelector = CameraSelector
                .Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            try {

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )

                preview.setSurfaceProvider(view_finder.createSurfaceProvider())

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(activity))
    }

    private fun takePhoto() {

        val imageCapture = imageCapture ?: return

        val photoFile = File(
            mOutputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                .format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture
            .OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(activity),
            object : ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val action = AddPostContainerDirections.actionEditImage(savedUri.toString())
                    findNavController().navigate(action)
                }

                override fun onError(exc: ImageCaptureException) {
                    exc.printStackTrace()
                }
            })
    }

    private fun getOutputDirectory(): File {
        val mediaDir = activity?.externalMediaDirs?.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir
        else requireActivity().filesDir
    }
}
