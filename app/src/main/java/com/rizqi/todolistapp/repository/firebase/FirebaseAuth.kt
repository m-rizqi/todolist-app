package com.rizqi.todolistapp.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rizqi.todolistapp.R
import com.rizqi.todolistapp.callback.FirebaseUserCallbackFailed
import com.rizqi.todolistapp.callback.FirebaseUserCallbackSuccess
import com.rizqi.todolistapp.repository.firebase.FirebaseAuthResultContract
import com.rizqi.todolistapp.repository.model.User
import com.rizqi.todolistapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

fun registerEmailPassword(name: String, email: String, password: String, confirmPassword: String, firebase: Firebase, firebaseUserCallbackSuccess: FirebaseUserCallbackSuccess, firebaseUserCallbackFailed: FirebaseUserCallbackFailed){
    val auth = firebase.auth
    // validation
    val valid = (name.isNotBlank()) and (email.isNotBlank()) and (password.isNotBlank()) and (password == confirmPassword)
    if (valid){

    }else{
        val exception = Exception("Please! fill form properly")
        firebaseUserCallbackFailed.onCallback(exception = exception)
    }
}

fun loginEmailPassword(email: String, password:String, firebase: Firebase, firebaseUserCallbackSuccess: FirebaseUserCallbackSuccess, firebaseUserCallbackFailed: FirebaseUserCallbackFailed){
    val auth = firebase.auth
    // validation
    val valid = (email.isNotBlank()) and (password.isNotBlank())
    if(valid){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.d(TAG, "signInWithEmail:success")
                    val firebaseUser = auth.currentUser
                    auth.currentUser?.let { firebaseUserCallbackSuccess.onCallback(it) }
                }else{
                    Log.d(TAG, "signInWithEmail:failure", task.exception)
                    task.exception?.let { firebaseUserCallbackFailed.onCallback(exception = it) }
                }
            }
    }else{
        val exception = Exception("Email or Password Not Valid")
        firebaseUserCallbackFailed.onCallback(exception = exception)
    }
}

fun getGoogleSignInClient(context: Context): GoogleSignInClient{
    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail().build()
    return GoogleSignIn.getClient(context, signInOptions)
}

@Composable
fun GoogleAuthKit(onClick: () -> Unit) {
    val authViewModel = AuthViewModel()
    val coroutineScope = rememberCoroutineScope()
    var text by remember{ mutableStateOf<String?>(null)}
    val user by remember(authViewModel){authViewModel.user}.collectAsState()
    val signInRequestCode = 1
    val authResultLauncher = rememberLauncherForActivityResult(contract = FirebaseAuthResultContract()){
            task ->
        try {
            val account = task?.getResult(ApiException::class.java)
            if (account == null){
                text = "Google Sign In Failed"
            }else{
                coroutineScope.launch {
                    authViewModel.signIn(account.id, account.displayName, account.email,
                        account.photoUrl.toString(),null)
                    Log.d(ContentValues.TAG,"user account:${user?.toString()}")
                }
            }
        }catch (e: ApiException){
            text = "Google Sign In Failed"
        }
    }
    user?.let {
    }

    IconButton(
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(55.dp),
            painter = painterResource(id = R.drawable.ic_google_logo),
            contentDescription = "Google Button",
            tint = Color.Unspecified,
        )
    }

}

@Composable
fun GoogleIconButton(
    onClick:() -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(55.dp),
            painter = painterResource(id = R.drawable.ic_google_logo),
            contentDescription = "Google Button",
            tint = Color.Unspecified,
        )
    }
}