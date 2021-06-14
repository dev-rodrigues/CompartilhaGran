package br.edu.compartilhagran.ui.home.addapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.compartilhagran.R
import br.edu.compartilhagran.domain.entity.Annotation
import java.text.SimpleDateFormat

class AnnotationAddapter(var annotations: ArrayList<Annotation>, private val actionClick: (Annotation) -> Unit): RecyclerView.Adapter<AnnotationAddapter.ViewHolder>() {

    override fun getItemCount() = annotations.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var annotationTitle: TextView = itemView.findViewById(R.id.annotationTitle)
        var annotationDescription: TextView = itemView.findViewById(R.id.annotationDescription)
        var annotationDate: TextView = itemView.findViewById(R.id.annotationDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnotationAddapter.ViewHolder {
        return  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_annotation, parent, false))
    }

    override fun onBindViewHolder(holder: AnnotationAddapter.ViewHolder, position: Int) {
        val sdf = SimpleDateFormat("dd/M/yyyy")

        holder.annotationTitle.text = annotations[position].title
        holder.annotationDescription.text = annotations[position].description
        holder.annotationDate.text = sdf.format(annotations[position].createdAt)

        holder.itemView.setOnClickListener{
            actionClick(annotations[position])
        }
    }
}