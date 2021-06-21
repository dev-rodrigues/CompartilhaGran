package br.edu.compartilhagran.ui.document

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import br.edu.compartilhagran.R
import br.edu.compartilhagran.domain.objectvalue.InputText
import br.edu.compartilhagran.infrastructure.service.impl.OpenFileEncryptedImpl
import br.edu.compartilhagran.ui.directory.dto.DirectoryDTO
import kotlinx.android.synthetic.main.document_fragment.*
import java.io.*

class DocumentFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.document_fragment, container, false)

        val fileDirectory = DirectoryDTO.directory!!

        val lines = OpenFileEncryptedImpl().execute(fileDirectory.name, requireContext())

        view.findViewById<TextView>(R.id.textEditDocumentTitle).setText(lines[0])
        view.findViewById<TextView>(R.id.textEditDocumentDescription).setText(lines[1])

        return view
    }




}