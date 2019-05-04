package me.kevincampos.insurancepolicies.injection.module

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import me.kevincampos.cache.PolicyEventCacheImpl
import me.kevincampos.cache.database.InsurancePoliciesDatabase
import me.kevincampos.data.cache.PolicyEventCache

@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDatabase(application: Application): InsurancePoliciesDatabase {
            return InsurancePoliciesDatabase.getInstance(application)
        }
    }

    @Binds
    abstract fun bindPolicyEventCache(policyEventCache: PolicyEventCacheImpl): PolicyEventCache

}