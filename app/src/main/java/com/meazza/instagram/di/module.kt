package com.meazza.instagram.di

import com.meazza.instagram.data.network.*
import com.meazza.instagram.ui.add_post.create_post.CreatePostViewModel
import com.meazza.instagram.ui.auth.log_in.LogInViewModel
import com.meazza.instagram.ui.auth.reset_password.ResetPasswordViewModel
import com.meazza.instagram.ui.auth.sign_up.SignUpViewModel
import com.meazza.instagram.ui.current_profile.CurrentProfileViewModel
import com.meazza.instagram.ui.current_profile.edit_profile.EditProfileViewModel
import com.meazza.instagram.ui.direct_message.DirectViewModel
import com.meazza.instagram.ui.direct_message.chat.ChatViewModel
import com.meazza.instagram.ui.explore.ExploreViewModel
import com.meazza.instagram.ui.explore.search.SearchViewModel
import com.meazza.instagram.ui.feed.FeedViewModel
import com.meazza.instagram.ui.post.PostsViewModel
import com.meazza.instagram.ui.post_detail.PostDetailViewModel
import com.meazza.instagram.ui.profile.ProfileViewModel
import com.meazza.instagram.ui.profile.follower_list.FollowersViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single { AuthService }
    single { CurrentUserDB }
    viewModel { LogInViewModel(get()) }
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { ResetPasswordViewModel(get()) }
}

@ExperimentalCoroutinesApi
val userModule = module {
    viewModel { EditProfileViewModel(get()) }
}

val profileModule = module {
    single { FollowActionDB }
    viewModel { ProfileViewModel(get()) }
    viewModel { CurrentProfileViewModel() }
    viewModel { FollowersViewModel() }
}

val postModule = module {
    single { PostActionDB }
    viewModel { PostsViewModel(get()) }
    viewModel { PostDetailViewModel() }
    viewModel { CreatePostViewModel(get(), get()) }
}

val feedModule = module {
    viewModel { FeedViewModel(get(), get()) }
}

val searchModule = module {
    single { RequestData }
    viewModel { ExploreViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}

@ExperimentalCoroutinesApi
val directMessageModule = module {
    single { MessagingDB }
    viewModel { DirectViewModel(get()) }
    viewModel { ChatViewModel(get(), get()) }
}
