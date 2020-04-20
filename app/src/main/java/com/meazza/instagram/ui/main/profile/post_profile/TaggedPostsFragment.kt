package com.meazza.instagram.ui.main.profile.post_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.meazza.instagram.R

/**
 * A simple [Fragment] subclass.
 */
class TaggedPostsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tagged_posts, container, false)
    }

}
