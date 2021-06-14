package br.edu.compartilhagran.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import br.edu.compartilhagran.R
import br.edu.compartilhagran.databinding.FragmentHomeBinding
import br.edu.compartilhagran.domain.entity.Annotation
import br.edu.compartilhagran.infrastructure.service.AnnotationService
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService
import br.edu.compartilhagran.infrastructure.service.impl.AnnotationServiceImpl
import br.edu.compartilhagran.infrastructure.service.impl.FirebaseAuthServiceImpl

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var firebaseAuthService: FirebaseAuthService
    private lateinit var annotationService: AnnotationService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_home, container, false)

        firebaseAuthService = FirebaseAuthServiceImpl()
        annotationService = AnnotationServiceImpl()

        val homeViewModelFactory = HomeViewModelFactory(firebaseAuthService, annotationService)
        viewModel = ViewModelProvider(requireActivity(), homeViewModelFactory).get(HomeViewModel::class.java)

        viewModel.findAnnotationsToUser();

        val viewList = inflate.findViewById<RecyclerView>(R.id.annotations)

        viewModel.annotations.observe(viewLifecycleOwner, Observer {
            configureViewList(it, viewList)
        })

        return inflate;
    }

    private fun configureViewList(it: List<Annotation>?, viewList: RecyclerView?) {

    }
}