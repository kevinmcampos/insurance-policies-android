package me.kevincampos.insurancepolicies.vehiclelist

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.kevincampos.insurancepolicies.R
import me.kevincampos.insurancepolicies.databinding.ItemHeaderBinding
import me.kevincampos.insurancepolicies.databinding.ItemVehicleBinding
import me.kevincampos.presentation.model.VehicleListItem
import me.kevincampos.presentation.model.VehicleWithPoliciesView

class VehiclesAdapter(
    private val onItemSelected: (VehicleWithPoliciesView) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var vehicleListItems = arrayListOf<VehicleListItem>()

    fun swapSection(newPolicyEvents: List<VehicleListItem>) {
        val calculateDiff = DiffUtil.calculateDiff(
            VehicleDiffCallback(
                vehicleListItems.toList(),
                newPolicyEvents
            )
        )
        calculateDiff.dispatchUpdatesTo(this)

        this.vehicleListItems.clear()
        this.vehicleListItems.addAll(newPolicyEvents)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return vehicleListItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (vehicleListItems[position].isHeader()) 0 else 1
    }

    override fun onCreateViewHolder(parentView: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parentView.context)
        when (viewType) {
            0 -> {
                val binding =
                    DataBindingUtil.inflate<ItemHeaderBinding>(
                        inflater,
                        R.layout.item_header, parentView, false
                    )

                return HeaderViewHolder(binding)
            }
            1 -> {
                val binding =
                    DataBindingUtil.inflate<ItemVehicleBinding>(
                        inflater,
                        R.layout.item_vehicle, parentView, false
                    )

                return VehicleViewHolder(binding)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is VehicleViewHolder && vehicleListItems[position] is VehicleWithPoliciesView) {
            viewHolder.onBind(vehicleListItems[position] as VehicleWithPoliciesView, onItemSelected)
        } else if (viewHolder is HeaderViewHolder && vehicleListItems[position] is HeaderView) {
            viewHolder.onBind(vehicleListItems[position] as HeaderView)
        }
    }

    class VehicleViewHolder(private val binding: ItemVehicleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(vehicleWithPolicies: VehicleWithPoliciesView, onItemSelected: (VehicleWithPoliciesView) -> Unit) {
            binding.vehicle = vehicleWithPolicies

            itemView.setOnClickListener {
                onItemSelected(vehicleWithPolicies)
            }
        }
    }

    class HeaderViewHolder(private val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(header: HeaderView) {
            binding.headerView = header
        }
    }

}
