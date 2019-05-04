package me.kevincampos.insurancepolicies.injection.module

import dagger.Binds
import dagger.Module
import me.kevincampos.data.PolicyEventEventRepositoryImpl
import me.kevincampos.domain.PolicyEventRepository

@Module
abstract class DataModule {

    @Binds
    abstract fun bindTransactionRepository(repository: PolicyEventEventRepositoryImpl): PolicyEventRepository

}