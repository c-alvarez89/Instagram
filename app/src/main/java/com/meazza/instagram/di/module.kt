package com.meazza.instagram.di

import com.meazza.instagram.data.network.*
import com.meazza.instagram.ui.auth.log_in.LogInViewModel
import com.meazza.instagram.ui.auth.reset_password.ResetPasswordViewModel
import com.meazza.instagram.ui.auth.sign_up.SignUpViewModel
import com.meazza.instagram.ui.direct_message.DirectViewModel
import com.meazza.instagram.ui.direct_message.chat.ChatViewModel
import com.meazza.instagram.ui.profile.ProfileViewModel
import com.meazza.instagram.ui.profile.own_profile.OwnInfoViewModel
import com.meazza.instagram.ui.search.SearchViewModel
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

val userModule = module {
    viewModel { OwnInfoViewModel(get()) }
}

val searchModule = module {
    single { RequestData }
    viewModel { SearchViewModel(get()) }
}

val profileModule = module {
    single { FollowActionDB }
    viewModel { ProfileViewModel(get()) }
}

@ExperimentalCoroutinesApi
val directMessageModule = module {
    single { MessagingDB }
    viewModel { ChatViewModel(get(), get()) }
    viewModel { DirectViewModel(get()) }
}
