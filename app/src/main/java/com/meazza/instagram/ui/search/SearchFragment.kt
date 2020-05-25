package com.meazza.instagram.ui.search

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.meazza.instagram.R
import com.meazza.instagram.common.listener.OnUserClickListener
import com.meazza.instagram.data.model.User
import com.meazza.instagram.databinding.FragmentSearchBinding
import com.meazza.instagram.ui.search.adapter.SearchAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

class SearchFragment : Fragment(R.layout.fragment_search), OnUserClickListener {

    private val searchViewModel by inject<SearchViewModel>()

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentSearchBinding>(view)?.apply {
            lifecycleOwner = this@SearchFragment
            viewModel = searchViewModel
        }

        searchViewModel.run {
            adapter.value = SearchAdapter(this@SearchFragment)
            textQuery.observe(viewLifecycleOwner, Observer { text ->
                getUserSearch(text).observe(viewLifecycleOwner, Observer {
                    setAdapter(it)
                })
            })
        }
    }

    override fun onItemClickListener(user: User) {
        val action = SearchFragmentDirections.gotoProfile(user)
        findNavController().navigate(action)
    }
}
