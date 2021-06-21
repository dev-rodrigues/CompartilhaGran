package br.edu.compartilhagran.ui.directory.addapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import br.edu.compartilhagran.R
import br.edu.compartilhagran.infrastructure.service.data.FileDTO
import br.edu.compartilhagran.ui.directory.DirectoryViewModel
import kotlin.collections.ArrayList

class DirectoryAddapter(
    var files: ArrayList<FileDTO>,
    var context: Context,
    var viewModel: DirectoryViewModel,
    private val actionClick: (FileDTO) -> Unit
): RecyclerView.Adapter<DirectoryAddapter.ViewHolder>() {

    override fun getItemCount() = files.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var textViewFileName: TextView = itemView.findViewById(R.id.textViewFileName)
        var textViewFileDate: TextView = itemView.findViewById(R.id.textViewFileDate)
        var buttonDelete: Button = itemView.findViewById(R.id.button_delete_document)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryAddapter.ViewHolder {
        return  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_file, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DirectoryAddapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            actionClick(files[position])
        }

        holder.buttonDelete.setOnClickListener {
            viewModel.deleteFile(files[position].name, context)
            viewModel.findFilesToDirectory(context)
        }
    }
}