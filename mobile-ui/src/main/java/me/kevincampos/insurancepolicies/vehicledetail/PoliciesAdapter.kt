package me.kevincampos.insurancepolicies.vehicledetail

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.kevincampos.insurancepolicies.R
import me.kevincampos.insurancepolicies.databinding.ItemPolicyBinding
import me.kevincampos.presentation.model.PolicyView

class PoliciesAdapter(
    private val onItemSelected: (PolicyView) -> Unit
) : RecyclerView.Adapter<PoliciesAdapter.PolicyViewHolder>() {

    private var policies = arrayListOf<PolicyView>()

    fun swap(newPolicies: List<PolicyView>) {
        this.policies.clear()
        this.policies.addAll(newPolicies)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return policies.size
    }

    override fun onCreateViewHolder(parentView: ViewGroup, viewType: Int): PolicyViewHolder {
        val inflater = LayoutInflater.from(parentView.context)
        val binding =
            DataBindingUtil.inflate<ItemPolicyBinding>(inflater, R.layout.item_policy, parentView, false)

        return PolicyViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: PolicyViewHolder, position: Int) {
        viewHolder.onBind(policies[position], onItemSelected)
    }

    class PolicyViewHolder(private val binding: ItemPolicyBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(policy: PolicyView, onItemSelected: (PolicyView) -> Unit) {
            binding.policy = policy

            itemView.setOnClickListener {
                onItemSelected(policy)
            }
        }
    }

}
