package com.williamsel.mathstack.core.di

import android.content.Context
import android.net.ConnectivityManager
import com.williamsel.mathstack.core.domain.repository.IConnectivityRepository
import com.williamsel.mathstack.core.network.ConnectivityRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityRepo(
        impl: ConnectivityRepositoryImpl
    ): IConnectivityRepository

    companion object {
        @Provides
        @Singleton
        fun provideConnectivityManager(
            @ApplicationContext ctx: Context
        ): ConnectivityManager =
            ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}
