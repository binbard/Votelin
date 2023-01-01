package com.utilman.votelin.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utilman.votelin.MainActivity
import com.utilman.votelin.R
import java.util.*

class AuthorizedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_authorized, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                requireActivity().supportFragmentManager.popBackStack()
                activity!!.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                activity!!.finish()
            }
        }, 1200)
    }



}
