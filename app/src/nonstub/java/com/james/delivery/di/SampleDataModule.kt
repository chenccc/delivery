package com.james.delivery.di

import android.content.Context
import com.james.delivery.StubInterceptor
import com.james.delivery.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SampleDataModule {
    @Provides
    @Singleton
    @Named(Constants.STUB_INTERCEPTOR_KEY)
    @JvmStatic
    fun provideStubInterceptor(
        @ApplicationContext context: Context
    ): Interceptor {
        return StubInterceptor()
    }
}