package br.edu.compartilhagran.infrastructure.service

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthService {
    fun getUser(): FirebaseUser;
    fun signIn(email: String, password: String): Task<AuthResult>;
    fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult>;
    fun isLogged(): Boolean;
    fun logout();
}