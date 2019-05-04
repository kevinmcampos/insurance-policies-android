package me.kevincampos.insurancepolicies.injection.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.kevincampos.insurancepolicies.policytransactions.PolicyTransactionsActivity
import me.kevincampos.insurancepolicies.vehicledetail.VehicleDetailActivity
import me.kevincampos.insurancepolicies.vehiclelist.VehicleListActivity

@Module
abstract class UiModule {

    @ContributesAndroidInjector
    abstract fun contributesVehicleListActivity(): VehicleListActivity

    @ContributesAndroidInjector
    abstract fun contributesVehicleDetailActivity(): VehicleDetailActivity

    @ContributesAndroidInjector
    abstract fun contributesPolicyTransactionsActivity(): PolicyTransactionsActivity

}