package com.utilman.votelin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.utilman.votelin.home.HomeActivity
import com.utilman.votelin.login.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(FirebaseAuth.getInstance().currentUser == null){
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            startActivity(intent)
        }
        else{
            Toast.makeText(this,"Already Logged in",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity,HomeActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}