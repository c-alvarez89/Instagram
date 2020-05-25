package com.meazza.instagram.ui.direct_message

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.meazza.instagram.R
import com.meazza.instagram.common.listener.OnUserClickListener
import com.meazza.instagram.data.model.User
import com.meazza.instagram.databinding.FragmentDirectBinding
import com.meazza.instagram.ui.direct_message.adapter.DirectAdapter
import com.meazza.instagram.util.setToolbarWithBackArrow
import kotlinx.android.synthetic.main.fragment_direct.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

@ExperimentalCoroutinesApi
class DirectFragment : Fragment(R.layout.fragment_direct), OnUserClickListener {

    private val directViewModel by inject<DirectViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentDirectBinding>(view)?.apply {
            lifecycleOwner = this@DirectFragment
            viewModel = directViewModel
        }

        directViewModel.run {
            adapter.value = DirectAdapter(this@DirectFragment)
            getConversations().observe(viewLifecycleOwner, Observer {
                setAdapter(it)
                Log.d("direct", "Users : ${it.size}")
            })
        }

        setHasOptionsMenu(true)
        setToolbarWithBackArrow(activity, tb_direct, getString(R.string.direct))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_direct, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onItemClickListener(user: User) {
        val action = DirectFragmentDirections.gotoChat(user)
        findNavController().navigate(action)
    }
}
