package com.mapx.kosten.mosimpa.di.modules.login

import com.mapx.kosten.mosimpa.presentation.activities.login.LoginActivity
import dagger.Subcomponent

@LoginScope
@Subcomponent(modules = [LoginModule::class])
interface LoginSubComponent {
    fun inject(loginActivity: LoginActivity)
}