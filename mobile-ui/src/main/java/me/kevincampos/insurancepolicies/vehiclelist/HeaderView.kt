package me.kevincampos.insurancepolicies.vehiclelist

import me.kevincampos.presentation.model.VehicleListItem

class HeaderView(val title: String) : VehicleListItem {

    override val id: String
        get() = title

    override fun isHeader() = true

}