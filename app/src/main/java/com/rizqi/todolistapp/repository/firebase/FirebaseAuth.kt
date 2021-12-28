package com.rizqi.todolistapp.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rizqi.todolist.nav.Screen
import com.rizqi.todolistapp.R
import com.rizqi.todolistapp.callback.FirebaseUserCallbackFailed
import com.rizqi.todolistapp.callback.FirebaseUserCallbackSuccess
import com.rizqi.todolistapp.repository.firebase.FirebaseAuthResultContract
import com.rizqi.todolistapp.ui.view.Home
import com.rizqi.todolistapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

fun loginEmailPassword(email: String, password:String, firebase: Firebase, firebaseUserCallbackSuccess: FirebaseUserCallbackSuccess, firebaseUserCallbackFailed: FirebaseUserCallbackFailed){
    // validation
    val auth = firebase.auth
    var valid = (email.isNotBlank()) and (password.trim().isNotBlank()) and (password.length >= 8)
    if(valid){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.d(TAG, "signInWithEmail:success")
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
fun GoogleAuthKit(authViewModel: AuthViewModel, navHostController: NavHostController) {
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
                }
            }
        }catch (e:ApiException){
            text = "Google Sign In Failed"
        }
    }
    GoogleIconButton {
        authResultLauncher.launch(signInRequestCode)
        if(user == null){
            Log.d(TAG, "User: Null")
        }else{
            Log.d(TAG, "User: ${user.toString()}")
        }
    }
}

@Composable
fun GoogleIconButton(
    onClick:() -> Unit
) {
    IconButton(onClick = {onClick()}) {
        Icon(
            modifier = Modifier.size(55.dp),
            painter = painterResource(id = R.drawable.ic_google_logo),
            contentDescription = "Google Button",
            tint = Color.Unspecified,
        )
    }
}