package br.edu.compartilhagran.ui.dashboard

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.compartilhagran.R
import br.edu.compartilhagran.domain.objectvalue.InputText
import br.edu.compartilhagran.domain.service.InputTextValidation
import br.edu.compartilhagran.domain.service.impl.InputTextValidationImpl
import br.edu.compartilhagran.infrastructure.service.AnnotationService
import br.edu.compartilhagran.infrastructure.service.FindWeatherService
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService
import br.edu.compartilhagran.infrastructure.service.StoreFile
import br.edu.compartilhagran.infrastructure.service.impl.AnnotationServiceImpl
import br.edu.compartilhagran.infrastructure.service.impl.FindWeatherServiceImpl
import br.edu.compartilhagran.infrastructure.service.impl.FirebaseAuthServiceImpl
import br.edu.compartilhagran.infrastructure.service.impl.StoreFileImpl
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment(), OnMapReadyCallback {

    private lateinit var viewModel: DashboardViewModel

    private val PERMISSION_LOCATION = Manifest.permission_group.LOCATION
    private val CAMERA_REQUEST_CODE = 0
    private val REQUEST_CAMERA = 100
    private val REQUEST_LOCATION = 200
    private var LATITUDE: Double? =null
    private var LONGITUDE: Double? =null
    private var bitmapPicture: Bitmap? = null

    private lateinit var firebaseAuthService: FirebaseAuthService
    private lateinit var annotationService: AnnotationService
    private lateinit var findWeatherService: FindWeatherService
    private lateinit var inputTextValidation: InputTextValidation
    private lateinit var storeFile: StoreFile

    private lateinit var gMap: GoogleMap
    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_dashboard, container, false)

        firebaseAuthService = FirebaseAuthServiceImpl()
        annotationService = AnnotationServiceImpl()
        inputTextValidation = InputTextValidationImpl()
        findWeatherService = FindWeatherServiceImpl()
        storeFile = StoreFileImpl()

        configureViewModel()
        savePicture(inflate)
        configureButtonLocation(inflate)

        return inflate
    }

    private fun configureButtonLocation(inflate: View) {
        val find_localization = inflate.findViewById<Button>(R.id.find_localization)

        find_localization.setOnClickListener {

            if(checkSelfPermission(requireContext(), PERMISSION_LOCATION) == -1) {
                getCurrentLocation()
            } else {
                if(shouldShowRequestPermissionRationale(Manifest.permission_group.LOCATION)) {
                    println("É necessário o recurso de localização")
                }
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION
                )
            }
        }
    }

    private fun getCurrentLocation() {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val GPS_PROVIDER = LocationManager.GPS_PROVIDER

        val gpsEnable = locationManager.isProviderEnabled(GPS_PROVIDER)

        when {
            gpsEnable -> {
                getCurrentLocationProvider(locationManager, GPS_PROVIDER)
            }
            else -> Toast.makeText(requireContext(), "GPS desabilitado", Toast.LENGTH_LONG).show()
        }

    }

    private fun getCurrentLocationProvider(locationManager: LocationManager, gpsProvider: String) {
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1L, 0f, locationListener
            )
        } catch (e: SecurityException) {
            Log.e("PERMISSÃO", e.message)
            Toast.makeText(requireContext(), "Deu merda", Toast.LENGTH_LONG).show()
        }
    }

    fun Double.format(digits: Int) = "%.${digits}f".format(this)

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            if (location != null && textViewLatitudeLongitude != null) {
                LATITUDE = location.latitude
                LONGITUDE = location.longitude

                val LatitudeToString = LATITUDE?.format(3).toString()
                val LongitudeToString = LONGITUDE?.format(3).toString()
                textViewLatitudeLongitude.text = "LATITUDE$LatitudeToString : LONGITUDE$LongitudeToString"

                val position = LatLng(LATITUDE!!, LONGITUDE!!)
                gMap.addMarker(MarkerOptions().position(position).title("Fim de mundo"))
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15f))
            }
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        }

        override fun onProviderEnabled(p0: String?) {
        }

        override fun onProviderDisabled(p0: String?) {
        }
    }

    private fun savePicture(inflate: View) {
        val photoImageView = inflate.findViewById<ImageView>(R.id.photoImageView)
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
        val dashboardViewModelFactory = DashboardViewModelFactory(firebaseAuthService, annotationService, findWeatherService)
        viewModel = ViewModelProvider(
            this,
            dashboardViewModelFactory
        ).get(DashboardViewModel::class.java)

        viewModel.status.observe(viewLifecycleOwner, {
            if (it)
                findNavController().navigate(R.id.navigation_home)
        })

        viewModel.msg.observe(viewLifecycleOwner, {
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.mapView)

        if (mapView != null) {
            mapView.onCreate(null)
            mapView.onResume()
            mapView.getMapAsync(this)
        }


        buttonSave.setOnClickListener {
            val editTextTextAnnotationTitle = editTextTextAnnotationTitle.text.toString()
            val editTextTextAnnotationDescription = editTextTextAnnotationDescription.text.toString()

            val inputs = ArrayList<InputText>()
            inputs.addAll(
                listOf(
                    InputText("title", editTextTextAnnotationTitle),
                    InputText("descrição", editTextTextAnnotationDescription)
                )
            )

            val responseValidation = inputTextValidation.validate(inputs)!!
            responseValidation.addAll(inputTextValidation.validate(bitmapPicture)!!)

            if (responseValidation.isEmpty()) {
                savePictureInDirectory(editTextTextAnnotationTitle)
                saveLocation(inputs)
                viewModel.saveAnnotation(editTextTextAnnotationTitle, editTextTextAnnotationDescription, getB64EncondeImage()!!, LATITUDE, LONGITUDE)
            } else {
                responseValidation.forEach {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun saveLocation(inputs: ArrayList<InputText>) {
        storeFile.execute(inputs, requireContext())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getB64EncondeImage(): String? {
        val baos = ByteArrayOutputStream()
        bitmapPicture!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.getEncoder().encodeToString(b)
    }

    private fun savePictureInDirectory(editTextTextAnnotationTitle: String) {
        val file = File(
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            editTextTextAnnotationTitle + Calendar.getInstance().time.toString() + ".jpeg"
        )
        val fOut = FileOutputStream(file)
        bitmapPicture?.compress(Bitmap.CompressFormat.JPEG, 85, fOut)

        fOut.flush()
        fOut.close()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(requireContext())
        gMap = googleMap
        if (LATITUDE != null && LONGITUDE != null) {
            val position = LatLng(LATITUDE!!, LONGITUDE!!)
            gMap.addMarker(MarkerOptions().position(position).title("Fim de mundo"))
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15f))
        }
    }
}