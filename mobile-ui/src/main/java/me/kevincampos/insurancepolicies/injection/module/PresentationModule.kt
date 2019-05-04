package me.kevincampos.insurancepolicies.injection.module

import dagger.Module
import dagger.Provides
import me.kevincampos.presentation.util.CoroutinesDispatcherProvider

@Module
abstract class PresentationModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesCoroutines(): CoroutinesDispatcherProvider {
            return CoroutinesDispatcherProvider()
        }
    }
}