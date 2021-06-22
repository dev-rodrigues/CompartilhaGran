package br.edu.compartilhagran.ui.showAnnotation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import br.edu.compartilhagran.R
import br.edu.compartilhagran.ui.home.dto.AnnotationDTO
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.show_annotation_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class ShowAnnotationFragment : Fragment() {

    private lateinit var viewModel: ShowAnnotationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_annotation_fragment, container, false)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val annotation = AnnotationDTO?.annotationDTO
        val sdf = SimpleDateFormat("dd/M/yyyy")

        val createdAt = annotation?.createdAt
        val title =  annotation?.title
        val description = annotation?.description
        val urlImage = annotation?.urlImage

        Picasso.get().load(annotation?.linkImageTemperature).into(climaImage)
        textViewCountry.text = annotation?.country
        textViewRegion.text =  annotation?.region

        textShowTittle.text = title.toString()
        textShowDescription.text = description.toString()
        textShowDate.text = sdf.format(createdAt)
        temperature.text = annotation?.temperature.toString() + " ºC"

        if (!urlImage.isNullOrEmpty()) {
            val handleBitmap = handleBitmap(urlImage)
            imgShowAnnotation.setImageBitmap(handleBitmap)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleBitmap(photo: String): Bitmap {
        val byteArray: ByteArray = Base64.getDecoder().decode(photo)

        val bmImage = BitmapFactory.decodeByteArray(
            byteArray, 0,
            byteArray.size
        )
        return bmImage
    }
}