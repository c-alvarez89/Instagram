package com.meazza.instagram.ui.direct.chat

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.meazza.instagram.R
import com.meazza.instagram.databinding.FragmentChatBinding
import org.koin.android.ext.android.inject

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val chatViewModel by inject<ChatViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentChatBinding>(view)?.apply {
            lifecycleOwner = this@ChatFragment
            viewModel = chatViewModel
        }

        /*chatViewModel.fetchMessages().observe(viewLifecycleOwner, Observer {
            chatViewModel.setAdapter(it!!)
        })*/
    }
}
