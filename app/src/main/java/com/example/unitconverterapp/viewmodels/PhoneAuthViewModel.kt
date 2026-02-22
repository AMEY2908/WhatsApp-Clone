package com.example.unitconverterapp.viewmodels


import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.unitconverterapp.dependency.PhoneAuthUser
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.io.ByteArrayOutputStream

@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: FirebaseDatabase
) : ViewModel() {
    private var hasNavigated = false
    val isSaveSuccessful = mutableStateOf(false)

    private val _authState =
        MutableStateFlow<AuthState>(AuthState.Ideal)
    val authState = _authState.asStateFlow()
    private val _navigationEvent = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val userRef = database.reference.child("users")

    private var storedVerificationId:String?=null
    fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity
    ) {
        _authState.value = AuthState.Loading
        val option = object :
            PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(id, token)
                Log.d("PhoneAuth", "onCodeSent: id = $id")
                storedVerificationId=id
                _authState.value = AuthState.CodeSent(verificationId = id)
            }
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signWithCredential(credential, context = activity)
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                Log.e(
                    "PhoneAuth",
                    "Verification failed: ${exception.message}"
                )
                _authState.value = AuthState.Error(
                    exception.message ?: "Verification failed"
                )
            }

        }
        val phoneAuthOptions =
            PhoneAuthOptions.newBuilder(firebaseAuth)

                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(option)
                .build()
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
    }

    private fun signWithCredential(credential: PhoneAuthCredential, context: Context) {
        _authState.value = AuthState.Loading
        Log.d("PhoneAuth", "signWithCredential started")
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                Log.d("PhoneAuth", "signInWithCredential completed, success: ${task.isSuccessful}")
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Log.d("PhoneAuth", "user uid: ${user?.uid}")
                    markUserAsSignedIn(context)
//                    _authState.value = AuthState.Ideal
                    // Don't emit Success here, let fetchUserProfile do it once
                    fetchUserProfile(user?.uid ?: "")
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Sign-IN-Failed")
                    Log.e("PhoneAuth", "signInWithCredential failed: ${task.exception?.message}")
                }
            }
    }

    private fun markUserAsSignedIn(context: Context) {

        val sharedPreferences = context.getSharedPreferences(
            "app_prefs",
            Context.MODE_PRIVATE
        )
        sharedPreferences.edit().putBoolean("isSignedIn", true)
            .apply()
    }

    private fun fetchUserProfile(userID: String) {
        if (hasNavigated) return
        val userRef = userRef.child(userID)
        userRef.get().addOnSuccessListener { snapshot ->
            if (!hasNavigated) {
                hasNavigated = true
                _navigationEvent.tryEmit(Unit)
            }
        }.addOnFailureListener {
            _authState.value = AuthState.Error("Failed to fetch user profile")
        }
    }
    fun verifyCode(otp: String, context: Context) {
        Log.d("PhoneAuth", "verifyCode called, storedId: $storedVerificationId")
        val id = storedVerificationId
        if (id.isNullOrEmpty()) {
            Log.e("PhoneAuth", "Attempting to verify OTP - no verification ID")
            _authState.value = AuthState.Error("Verification not started or invalid ID")
            return
        }
        val credential = PhoneAuthProvider.getCredential(id, otp)
        signWithCredential(credential, context)
    }

    fun saveUserProfile(userID: String,name: String,status: String,profileImage:Bitmap?){
//        val database= FirebaseDatabase.getInstance().reference
        val encodeImage=profileImage?.let { convertBitmaptoBase64(it)}
        val userProfile= PhoneAuthUser(
            userId = userID,
            name = name,
            status = status,
            phoneNumber = Firebase.auth.currentUser?.phoneNumber ?: "",
            profileImage = encodeImage,
        )
        database.reference.child("users").child(userID).setValue(userProfile)
            .addOnSuccessListener { isSaveSuccessful.value=true }.addOnFailureListener { isSaveSuccessful.value=false }
    }
    private fun convertBitmaptoBase64(bitmap: Bitmap): String{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream)
        val byteArray=byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
    fun resetAuthState() {
        hasNavigated=false
        _authState.value = AuthState.Ideal
    }
    fun signOut(activity: Activity){
        firebaseAuth.signOut()
        val sharedPreferences = activity.getSharedPreferences(
            "app_prefs",
            Activity.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isSigned",false).apply()
    }
}


sealed class AuthState {
    object Ideal : AuthState()
    object Loading : AuthState()
    data class CodeSent(val verificationId: String) : AuthState()
    data class Success(val user: PhoneAuthUser) : AuthState()
    data class Error(val message: String) : AuthState()

}