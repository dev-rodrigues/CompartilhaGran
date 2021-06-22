package br.edu.compartilhagran.ui.home.addapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import br.edu.compartilhagran.R
import br.edu.compartilhagran.domain.entity.Annotation
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AnnotationAddapter(
    var annotations: ArrayList<Annotation>,
    private val actionClick: (Annotation) -> Unit
): RecyclerView.Adapter<AnnotationAddapter.ViewHolder>() {

    override fun getItemCount() = annotations.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var annotationTitle: TextView = itemView.findViewById(R.id.annotationTitle)
        var annotationDescription: TextView = itemView.findViewById(R.id.annotationDescription)
        var annotationDate: TextView = itemView.findViewById(R.id.annotationDate)

        var imageUpload: ImageView = itemView.findViewById(R.id.imageUpload)

        var txtUserNameAnnotation: TextView = itemView.findViewById(R.id.txtUserNameAnnotation)
        var txtNickNameAnnotation: TextView = itemView.findViewById(R.id.txtNickNameAnnotation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnotationAddapter.ViewHolder {
        return  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_annotation, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AnnotationAddapter.ViewHolder, position: Int) {
        val sdf = SimpleDateFormat("dd/MM/yyyy")

        holder.annotationTitle.text = annotations[position].title
        holder.annotationDescription.text = annotations[position].description
        holder.annotationDate.text = sdf.format(annotations[position].createdAt)
        holder.txtUserNameAnnotation.text = annotations[position].userName
        holder.txtNickNameAnnotation.text = annotations[position].nickName

        val urlImage = annotations[position].urlImage

        if (!urlImage.isNullOrEmpty()) {
            val handleBitmap = handleBitmap(urlImage)
            holder.imageUpload.setImageBitmap(handleBitmap)
        }

        holder.itemView.setOnClickListener{
            actionClick(annotations[position])
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