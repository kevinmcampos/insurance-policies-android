package me.kevincampos.insurancepolicies.vehiclelist

import android.support.v7.util.DiffUtil
import me.kevincampos.presentation.model.VehicleListItem

class VehicleDiffCallback(
    private val oldList: List<VehicleListItem>,
    private val newList: List<VehicleListItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldListPosition: Int, newListPosition: Int): Boolean {
        return oldList[oldListPosition].id == newList[newListPosition].id
    }

    override fun areContentsTheSame(oldListPosition: Int, newListPosition: Int): Boolean {
        return oldList[oldListPosition].id == newList[newListPosition].id
    }

}
