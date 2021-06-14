package br.edu.compartilhagran.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.compartilhagran.MainActivity
import br.edu.compartilhagran.R
import br.edu.compartilhagran.domain.objectvalue.InputText
import br.edu.compartilhagran.domain.service.InputTextValidation
import br.edu.compartilhagran.domain.service.impl.InputTextValidationImpl
import br.edu.compartilhagran.infrastructure.service.FirebaseAuthService
import br.edu.compartilhagran.infrastructure.service.impl.FirebaseAuthServiceImpl
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_fragment.*
import java.util.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel;
    private lateinit var callbackManager: CallbackManager

    private lateinit var firebaseAuthService: FirebaseAuthService;
    private lateinit var inputTextValidation: InputTextValidation;
    private lateinit var firebaseAuth: FirebaseAuth;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.login_fragment, container, false);


        firebaseAuthService = FirebaseAuthServiceImpl();
        inputTextValidation = InputTextValidationImpl();
        callbackManager = CallbackManager.Factory.create();


        configureInput(inflate)
        configureViewModel()


        return inflate;
    }

    private fun configureInput(inflate: View) {
        var signup = inflate.findViewById<TextView>(R.id.signup)
        var _login_facebook_button = inflate.findViewById<Button>(R.id.login_facebook_button)

        //login_facebook_button.setReadPermissions("email", "public_profile")
        signup.setOnClickListener {
            findNavController().navigate(R.id.signupFragment)
        }


        _login_facebook_button.setOnClickListener {
            signInFacebook()
        }

    }

    private fun signInFacebook() {
        login_facebook_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                handleFacebookAccessToken(loginResult!!.accessToken)
            }

            override fun onCancel() {
                println("cancelou")
            }

            override fun onError(exception: FacebookException) {
                println("error")
            }
        })
    }

    private fun handleFacebookAccessToken(accessToken: AccessToken?) {
        val credential = FacebookAuthProvider.getCredential(accessToken!!.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                if (it != null) {
                    val user = firebaseAuth.currentUser
                    findNavController().navigate(R.id.navigation_home)
                } else {
                    Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }



    private fun configureViewModel() {
        val loginViewModelFactory = LoginViewModelFactory(firebaseAuthService);
        viewModel = ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java);

        viewModel.status.observe(viewLifecycleOwner, Observer {
            if (it)
                findNavController().navigate(R.id.navigation_home)
        })

        viewModel.msg.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank())
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.ok.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val mainActivity = requireActivity() as MainActivity
                mainActivity.nav_view.visibility = View.VISIBLE
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonLogin.setOnClickListener {
            var editTextTextUsernameLogin = editTextTextUsernameLogin.text.toString();
            var editTextTextLoginPasswordLogin = editTextTextLoginPasswordLogin.text.toString();

            val validate = inputTextValidation.validate(
                Arrays.asList(
                    InputText("E-mail", editTextTextUsernameLogin),
                    InputText("Password", editTextTextLoginPasswordLogin)
                )
            );

            if (validate.isNullOrEmpty()) {
                viewModel.authenticate(editTextTextUsernameLogin, editTextTextLoginPasswordLogin)
            } else {
                validate.forEach {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
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