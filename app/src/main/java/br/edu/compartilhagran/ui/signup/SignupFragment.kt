package br.edu.compartilhagran.ui.signup

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.edu.compartilhagran.R
import br.edu.compartilhagran.domain.objectvalue.InputText
import br.edu.compartilhagran.domain.service.InputTextValidation
import br.edu.compartilhagran.domain.service.impl.InputTextValidationImpl
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService
import br.edu.compartilhagran.infrastructure.service.UserDetailService
import br.edu.compartilhagran.infrastructure.service.impl.FirebaseAuthServiceImpl
import br.edu.compartilhagran.infrastructure.service.impl.UserDetailServiceImpl
import kotlinx.android.synthetic.main.signup_fragment.*
import java.util.*
import kotlin.collections.ArrayList

class SignupFragment : Fragment() {

    private lateinit var viewModel: SignupViewModel;

    private lateinit var firebaseAuthService: FirebaseAuthService;
    private lateinit var userDetailService: UserDetailService;
    private lateinit var inputTextValidation: InputTextValidation;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.signup_fragment, container, false);

        firebaseAuthService = FirebaseAuthServiceImpl();
        userDetailService = UserDetailServiceImpl();
        inputTextValidation = InputTextValidationImpl();

        configureViewModel();
        configureNavigation(inflate);

        return inflate;
    }

    private fun configureNavigation(inflate: View) {
        var returnLogin = inflate.findViewById<TextView>(R.id.returnLogin)
        returnLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun configureViewModel() {
        val loginViewModelFactory = SignupViewModelFactory(firebaseAuthService, userDetailService)
        viewModel = ViewModelProvider(this, loginViewModelFactory).get(SignupViewModel::class.java);

        viewModel.status.observe(viewLifecycleOwner, Observer {
            if(it)
                findNavController().popBackStack()
        })

        viewModel.msg.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrBlank())
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        btnSignupRegister.setOnClickListener {
            var inputSignupFullName = inputSignupFullName.text.toString();
            var inputSignupEmail = inputSignupEmail.text.toString();
            var inputSignupPassword = inputSignupPassword.text.toString();

            val inputs = ArrayList<InputText>()

            inputs.addAll(Arrays.asList(
                InputText("FullName", inputSignupFullName),
                InputText("E-mail", inputSignupEmail),
                InputText("Password", inputSignupPassword),
            ))

            var validations = inputTextValidation.validate(inputs);

            if (validations.isNullOrEmpty()) {
                viewModel.register(inputSignupEmail, inputSignupPassword, inputSignupFullName);
            } else {
                validations.forEach {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

}