package com.meazza.instagram.ui.direct

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.meazza.instagram.R
import kotlinx.android.synthetic.main.fragment_direct.*

class DirectFragment : Fragment(R.layout.fragment_direct) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_message.setOnClickListener {
            findNavController().navigate(R.id.next_action)
        }
    }
}
