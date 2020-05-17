package com.mapx.kosten.mosimpa.di.modules.login

import com.mapx.kosten.mosimpa.presentation.activities.login.LoginViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class LoginModule {
    // @Provides
   /* fun provideGetCurrentUserUseCase(usersRepository: UsersRepository): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(
            ASyncTransformer(),
            usersRepository
        )
    }*/

    @Provides
    fun provideLoginViewModelFactory(
    ): LoginViewModelFactory {
        return LoginViewModelFactory()
    }
}