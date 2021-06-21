package br.edu.compartilhagran.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import br.edu.compartilhagran.R
import br.edu.compartilhagran.domain.entity.Annotation
import br.edu.compartilhagran.infrastructure.service.AnnotationService
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService
import br.edu.compartilhagran.infrastructure.service.impl.AnnotationServiceImpl
import br.edu.compartilhagran.infrastructure.service.impl.FirebaseAuthServiceImpl
import br.edu.compartilhagran.ui.home.addapter.AnnotationAddapter
import br.edu.compartilhagran.ui.home.dto.AnnotationDTO

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

        viewModel.findAnnotationsToUser()

        val viewList = inflate.findViewById<RecyclerView>(R.id.files)

        viewModel.annotations.observe(viewLifecycleOwner, {
            configureViewList(it, viewList)
        })

        return inflate
    }

    @SuppressLint("WrongConstant")
    private fun configureViewList(annotations: List<Annotation>, viewList: RecyclerView) {
        viewList.adapter = AnnotationAddapter(annotations as ArrayList<Annotation>) {
            AnnotationDTO.annotationDTO = it
            findNavController().navigate(R.id.showAnnotationFragment)
        }
        viewList.layoutManager = LinearLayoutManager(requireContext(), OrientationHelper.VERTICAL, false)
    }
}