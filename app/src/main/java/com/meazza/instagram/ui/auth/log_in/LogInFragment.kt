package com.meazza.instagram.ui.auth.log_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.meazza.instagram.R
import com.meazza.instagram.ui.MainHostActivity
import kotlinx.android.synthetic.main.fragment_log_in.*
import org.jetbrains.anko.support.v4.startActivity

class LogInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_login.setOnClickListener {
            startActivity<MainHostActivity>()
        }
    }

}