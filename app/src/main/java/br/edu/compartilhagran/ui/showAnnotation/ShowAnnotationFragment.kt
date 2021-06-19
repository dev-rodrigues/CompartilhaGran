package br.edu.compartilhagran.ui.showAnnotation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.compartilhagran.R
import br.edu.compartilhagran.ui.home.dto.AnnotationDTO

class ShowAnnotationFragment : Fragment() {

    private lateinit var viewModel: ShowAnnotationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_annotation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (AnnotationDTO.annotationDTO != null) {
            println(AnnotationDTO.annotationDTO)

        }
    }
}