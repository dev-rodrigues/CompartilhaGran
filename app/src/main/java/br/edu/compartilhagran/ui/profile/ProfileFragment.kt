package br.edu.compartilhagran.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.edu.compartilhagran.R
import br.edu.compartilhagran.domain.entity.Annotation
import br.edu.compartilhagran.domain.entity.UserDetail
import br.edu.compartilhagran.infrastructure.service.AnnotationService
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService
import br.edu.compartilhagran.infrastructure.service.UserDetailService
import br.edu.compartilhagran.infrastructure.service.impl.AnnotationServiceImpl
import br.edu.compartilhagran.infrastructure.service.impl.FirebaseAuthServiceImpl
import br.edu.compartilhagran.infrastructure.service.impl.UserDetailServiceImpl
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var userDetailService: UserDetailService
    private lateinit var annotationService: AnnotationService
    private lateinit var firebaseAuthService: FirebaseAuthService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)

        userDetailService = UserDetailServiceImpl()
        annotationService = AnnotationServiceImpl()
        firebaseAuthService = FirebaseAuthServiceImpl()

        val profileViewModelFactory = ProfileViewModelFactory(userDetailService, annotationService, firebaseAuthService)
        viewModel = ViewModelProvider(this, profileViewModelFactory).get(ProfileViewModel::class.java)

        viewModel.findDetailUser()
        viewModel.userDetails.observe(viewLifecycleOwner, Observer {
            var user = viewModel.userDetails.value?.get(0)!!
            fillFields(user)
        })

        viewModel.annotations.observe(viewLifecycleOwner, Observer {
            textViewPostsEdit.text = it.size.toString() + " publicações"
        })

        return view
    }

    private fun fillFields(user: UserDetail) {
        textViewEmailEdit.text = user.foreignKey
        textViewUserNameEdit.text = user.fullName
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureAds(view)
    }

    private fun configureAds(view: View) {
        MobileAds.initialize(requireContext())

        val adView = view.findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}