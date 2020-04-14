package com.meazza.instagram.ui.auth.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.meazza.instagram.R
import com.meazza.instagram.ui.MainHostActivity
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.jetbrains.anko.support.v4.startActivity

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_sign_up.setOnClickListener {
            startActivity<MainHostActivity>()
        }
    }

}
