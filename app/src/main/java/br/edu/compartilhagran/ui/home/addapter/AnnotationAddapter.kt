package br.edu.compartilhagran.ui.home.addapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.compartilhagran.domain.entity.Annotation

class AnnotationAddapter(var annotation: ArrayList<Annotation>, private val actionClick: (Annotation) -> Unit): RecyclerView.Adapter<AnnotationAddapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): AnnotationAddapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AnnotationAddapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = annotation.size
}