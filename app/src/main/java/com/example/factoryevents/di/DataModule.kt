package com.example.factoryevents.di

import android.content.Context
import com.example.factoryevents.data.reposiroty.HseRepositoryImpl
import com.example.factoryevents.domain.repository.HseRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: HseRepositoryImpl): HseRepository

    companion object{


        //это можно убрать сделано ради теста
        @ApplicationScope
        @Provides
        fun provideGoogleAccount(
            context: Context
        ): GoogleSignInAccount?{
            return GoogleSignIn.getLastSignedInAccount(context);
        }
    }
}