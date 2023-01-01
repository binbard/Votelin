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
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.utilman.votelin.R
import java.util.concurrent.TimeUnit


class GetPhoneFragment : Fragment() {
    private lateinit var etPhone: EditText
    private lateinit var btnNext: Button
    private lateinit var auth: FirebaseAuth
    private var verificationId: String? = null
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_get_phone, container, false)

        auth = FirebaseAuth.getInstance()

        etPhone = view.findViewById(R.id.et_phone)
        btnNext = view.findViewById(R.id.btn_next)

        btnNext.setOnClickListener {
            val phoneNumber = etPhone.text.toString()
            if (phoneNumber.length == 10) {
                sendOTP(phoneNumber)
            } else {
                Toast.makeText(context, "Please enter a valid phone number", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return view
    }

    private fun sendOTP(phoneNumber: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91$phoneNumber",
            60,
            TimeUnit.SECONDS,
            requireActivity(),
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(context, "Verification failed: ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onCodeSent(
                    id: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    verificationId = id
                    resendToken = token
                    val verificationFragment = VerifyOTPFragment()
                    val args = Bundle()
                    args.putString("phoneNumber", phoneNumber)
                    args.putString("verificationId", verificationId)
                    verificationFragment.arguments = args
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_login_process, verificationFragment)
                        .addToBackStack(null)
                        .commit()
                }
            })
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    val authorizedFragment = AuthorizedFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.fl_login_process, authorizedFragment)
                        .commit()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        view?.let {
                            Snackbar.make(it, "Could not Login", Snackbar.LENGTH_SHORT)
                                .setAction("OK") {
                                }
                                .show()
                        }
                    }
                }
            }
    }
}