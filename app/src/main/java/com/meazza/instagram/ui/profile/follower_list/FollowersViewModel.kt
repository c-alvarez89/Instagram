package com.meazza.instagram.ui.profile.follower_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meazza.instagram.ui.profile.follower_list.adapter.FollowerListAdapter

class FollowersViewModel : ViewModel() {

    val adapter = MutableLiveData<FollowerListAdapter>()
}
