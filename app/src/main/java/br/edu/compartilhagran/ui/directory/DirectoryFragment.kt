package br.edu.compartilhagran.ui.directory

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import br.edu.compartilhagran.R
import br.edu.compartilhagran.infrastructure.service.DeleteFileService
import br.edu.compartilhagran.infrastructure.service.FilesOnDirectory
import br.edu.compartilhagran.infrastructure.service.data.FileDTO
import br.edu.compartilhagran.infrastructure.service.impl.DeleteFileServiceImpl
import br.edu.compartilhagran.infrastructure.service.impl.FilesOnDirectoryImpl
import br.edu.compartilhagran.ui.directory.addapter.DirectoryAddapter
import br.edu.compartilhagran.ui.directory.dto.DirectoryDTO

class DirectoryFragment : Fragment() {

    private lateinit var viewModel: DirectoryViewModel
    private lateinit var filesOnDirectory: FilesOnDirectory
    private lateinit var deleteFileService: DeleteFileService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_directory, container, false)

        filesOnDirectory = FilesOnDirectoryImpl()
        deleteFileService = DeleteFileServiceImpl()

        configureViewModel()
        configureViewList(view)

        return view
    }

    private fun configureViewList(view: View) {
        val viewList = view.findViewById<RecyclerView>(R.id.files)
        viewModel.findFilesToDirectory(requireContext())
        viewModel.files.observe(viewLifecycleOwner, {
            configureViewList(it, viewList)
        })
    }

    private fun configureViewModel() {
        val directoryViewModel = DirectoryViewModelFactory(filesOnDirectory, deleteFileService)
        viewModel = ViewModelProvider(
            requireActivity(),
            directoryViewModel
        ).get(DirectoryViewModel::class.java)
    }

    @SuppressLint("WrongConstant")
    private fun configureViewList(files: List<FileDTO>, viewList: RecyclerView) {

        viewList.adapter = DirectoryAddapter(files as ArrayList<FileDTO>, requireContext(), viewModel) {
            DirectoryDTO.directory = it
        }

        viewList.layoutManager = LinearLayoutManager(requireContext(), OrientationHelper.VERTICAL, false)
    }
}