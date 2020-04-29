package com.meazza.instagram.ui.direct_message.chat

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.meazza.instagram.R
import com.meazza.instagram.databinding.FragmentChatBinding
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val chatViewModel by inject<ChatViewModel>()

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentChatBinding>(view)?.apply {
            lifecycleOwner = this@ChatFragment
            viewModel = chatViewModel
        }

        chatViewModel.fetchMessages().observe(viewLifecycleOwner, Observer {
            chatViewModel.setAdapter(it)
            rv_chat.smoothScrollToPosition(it.size)
        })

        chatViewModel.getUser()
    }
}
