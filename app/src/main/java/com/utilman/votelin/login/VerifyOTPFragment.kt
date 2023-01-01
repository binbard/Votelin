package com.utilman.votelin.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import com.utilman.votelin.R

class VerifyOTPFragment : Fragment() {

    private lateinit var etOtp: EditText
    private lateinit var btnNext1: Button

    private lateinit var auth: FirebaseAuth

    private var phoneNumber: String? = null
    private var verificationId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_verify_otp, container, false)

        auth = FirebaseAuth.getInstance()
        btnNext1 = view.findViewById(R.id.btn_next1)
        phoneNumber = arguments?.getString("phoneNumber")
        verificationId = arguments?.getString("verificationId")


        etOtp = view.findViewById(R.id.et_otp)

        btnNext1.setOnClickListener {
            val verificationCode = etOtp.text.toString()
            if (verificationCode.length == 6) {
                verifyOTP(verificationCode)
            } else {
                Toast.makeText(
                    context,
                    "Please enter a valid verification code",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return view
    }

    private fun verifyOTP(verificationCode: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, verificationCode)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    val authorizedFragment = AuthorizedFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_login_process, authorizedFragment)
                        .commit()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        view?.let {
                            Snackbar.make(it, "Incorrect OTP", Snackbar.LENGTH_SHORT)
                                .setAction("Dismiss") {
                                }
                                .show()
                        }
                    }
                }
            }
    }

}