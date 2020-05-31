package com.meazza.instagram.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.meazza.instagram.R
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUiAction()
    }

    private fun setUiAction() {
        btn_create_new_account.setOnClickListener {
            findNavController().navigate(R.id.action_sign_up)
        }
        tv_log_in.setOnClickListener {
            findNavController().navigate(R.id.action_login)
        }
    }
}
