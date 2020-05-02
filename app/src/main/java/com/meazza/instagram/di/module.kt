package com.meazza.instagram.di

import com.meazza.instagram.data.network.AuthService
import com.meazza.instagram.data.network.MessagingService
import com.meazza.instagram.data.network.RequestDB
import com.meazza.instagram.data.network.UserInstanceDB
import com.meazza.instagram.ui.auth.log_in.LogInViewModel
import com.meazza.instagram.ui.auth.reset_password.ResetPasswordViewModel
import com.meazza.instagram.ui.auth.sign_up.SignUpViewModel
import com.meazza.instagram.ui.direct_message.chat.ChatViewModel
import com.meazza.instagram.ui.profile.ProfileViewModel
import com.meazza.instagram.ui.profile.own_profile.OwnInfoViewModel
import com.meazza.instagram.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single { AuthService }
    single { UserInstanceDB }
    viewModel { LogInViewModel(get()) }
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { ResetPasswordViewModel(get()) }
}

val userModule = module {
    viewModel { OwnInfoViewModel(get()) }
}

val searchModule = module {
    single { RequestDB }
    viewModel { SearchViewModel(get()) }
}

val profileModule = module {
    viewModel { ProfileViewModel() }
}

val chatModule = module {
    single { MessagingService }
    viewModel { ChatViewModel(get(), get()) }
}
