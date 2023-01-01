package com.utilman.votelin.handlers

import android.app.Activity
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class AuthProvider {

    companion object {
        fun PhoneAuth(activty: Activity,phoneNumber: Int) {
            val auth = Firebase.auth
            lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber.toString())       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activty)                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)

        }
    }

}