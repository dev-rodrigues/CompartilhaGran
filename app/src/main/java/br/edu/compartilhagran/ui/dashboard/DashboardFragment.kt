package br.edu.compartilhagran.ui.dashboard

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.compartilhagran.R
import br.edu.compartilhagran.infrastructure.service.AnnotationService
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService
import br.edu.compartilhagran.infrastructure.service.impl.AnnotationServiceImpl
import br.edu.compartilhagran.infrastructure.service.impl.FirebaseAuthServiceImpl
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class DashboardFragment : Fragment() {

    private lateinit var viewModel: DashboardViewModel
    private val CAMERA_REQUEST_CODE = 0
    private val REQUEST_CAMERA = 100

    private lateinit var bitmapPicture: Bitmap

    private lateinit var firebaseAuthService: FirebaseAuthService
    private lateinit var annotationService: AnnotationService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_dashboard, container, false)

        firebaseAuthService = FirebaseAuthServiceImpl()
        annotationService = AnnotationServiceImpl()

        configureViewModel()

        savePicture(inflate)

        return inflate
    }

    private fun savePicture(inflate: View) {
        var photoImageView = inflate.findViewById<ImageView>(R.id.photoImageView)
        photoImageView.setOnClickListener {

            if(checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
                } else {
                    Toast.makeText(requireContext(), "CUCO!", Toast.LENGTH_SHORT).show()
                }

            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    println("É necessário o uso da câmera para esta funcionalidade.")
                }
                requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA)
            }
        }
    }

    private fun configureViewModel() {
        val dashboardViewModelFactory = DashboardViewModelFactory(firebaseAuthService, annotationService)
        viewModel = ViewModelProvider(requireActivity(), dashboardViewModelFactory).get(DashboardViewModel::class.java)

        viewModel.status.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it)
                findNavController().navigate(R.id.navigation_home)
        })

        viewModel.msg.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (!it.isNullOrEmpty())
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val takenImage = data?.extras?.get("data") as Bitmap

            bitmapPicture = takenImage
            photoImageView.setImageBitmap(bitmapPicture)

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSave.setOnClickListener {

            var editTextTextAnnotationTitle = editTextTextAnnotationTitle.text.toString()
            var editTextTextAnnotationDescription = editTextTextAnnotationDescription.text.toString()

            val file = File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), editTextTextAnnotationTitle + Calendar.getInstance().time.toString() + ".jpeg")
            val fOut = FileOutputStream(file)

            bitmapPicture.compress(Bitmap.CompressFormat.JPEG, 85, fOut)


            fOut.flush()
            fOut.close()


            var picture = bitmapPicture

            viewModel.saveAnnotation(editTextTextAnnotationTitle, editTextTextAnnotationDescription, picture)

        }
    }
}