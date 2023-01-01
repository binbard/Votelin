package com.utilman.votelin.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.utilman.votelin.R.layout.activity_login)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = GetPhoneFragment()
        fragmentTransaction.add(com.utilman.votelin.R.id.fl_login_process, fragment)
        fragmentTransaction.commit()

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            supportFragmentManager.popBackStack()
        } else {
//            super.onBackPressed()
        }
    }

}