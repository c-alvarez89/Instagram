package com.meazza.instagram.ui.add_post.share

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.api.load
import com.meazza.instagram.R
import kotlinx.android.synthetic.main.fragment_new_post.*

class NewPostFragment : Fragment(R.layout.fragment_new_post) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image = arguments?.let { NewPostFragmentArgs.fromBundle(it).bitmap }
        image?.let { iv_new_post.load(image) }

        setToolbar()
    }

    private fun setToolbar() {
        val mActivity = activity as AppCompatActivity
        setHasOptionsMenu(true)
        mActivity.apply {
            setSupportActionBar(tb_new_post)
            title = getString(R.string.new_post)
            supportActionBar?.run {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_arrow_left)
            }
        }
    }
}
