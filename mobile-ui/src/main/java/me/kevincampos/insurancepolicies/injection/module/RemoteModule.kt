package me.kevincampos.insurancepolicies.injection.module

import android.support.test.espresso.IdlingRegistry
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Binds
import dagger.Module
import dagger.Provides
import me.kevincampos.data.remote.PolicyEventRemote
import me.kevincampos.insurancepolicies.BuildConfig
import me.kevincampos.remote.PolicyEventRemoteImpl
import me.kevincampos.remote.service.PolicyEventService
import me.kevincampos.remote.service.PolicyEventServiceFactory
import okhttp3.OkHttpClient

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providePolicyEventService(okHttpClient: OkHttpClient): PolicyEventService {
            return PolicyEventServiceFactory.makePolicyEventService(okHttpClient)
        }

        @Provides
        @JvmStatic
        fun provideOkHttpClient(): OkHttpClient {
            val okHttpClient = PolicyEventServiceFactory.makeOkHttpClient(BuildConfig.DEBUG)

            if (BuildConfig.DEBUG) {
                IdlingRegistry.getInstance().register(OkHttp3IdlingResource.create("OkHttp", okHttpClient))

            }

            return okHttpClient
        }
    }

    @Binds
    abstract fun bindPolicyEventRemote(policyEventRemote: PolicyEventRemoteImpl): PolicyEventRemote

}