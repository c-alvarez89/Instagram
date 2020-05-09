package com.meazza.instagram.ui.direct_message.chat

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.meazza.instagram.R
import com.meazza.instagram.data.model.User
import com.meazza.instagram.databinding.FragmentChatBinding
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

@ExperimentalCoroutinesApi
class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val chatViewModel by inject<ChatViewModel>()

    private var instagrammer: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instagrammer = arguments?.let { ChatFragmentArgs.fromBundle(it).user }

        chatViewModel.run {
            instagrammerUser.value = instagrammer
            instagrammerName.value = instagrammer?.name
            instagrammerUsername.value = instagrammer?.username
            instagrammerPhoto.value = instagrammer?.photoUrl
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentChatBinding>(view)?.apply {
            lifecycleOwner = this@ChatFragment
            viewModel = chatViewModel
        }

        chatViewModel.run {
            instagrammer?.id?.let {
                fetchMessages(it).observe(viewLifecycleOwner, Observer { messages ->
                    setAdapter(messages)
                    rv_chat.smoothScrollToPosition(messages.size)
                })
            }
        }

        setHasOptionsMenu(true)
        setToolbar()
    }

    private fun setToolbar() {
        val mActivity = activity as AppCompatActivity
        mActivity.apply {
            setSupportActionBar(tb_chat)
            title = ""
            supportActionBar?.run {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_arrow_left)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_chat, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
