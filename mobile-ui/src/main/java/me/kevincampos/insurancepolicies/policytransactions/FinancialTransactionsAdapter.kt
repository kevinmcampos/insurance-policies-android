package me.kevincampos.insurancepolicies.policytransactions

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.kevincampos.insurancepolicies.R
import me.kevincampos.insurancepolicies.databinding.ItemFinancialTransactionBinding
import me.kevincampos.presentation.model.FinancialTransactionView

class FinancialTransactionsAdapter(
    private val onItemSelected: (FinancialTransactionView) -> Unit
) : RecyclerView.Adapter<FinancialTransactionsAdapter.FinancialTransactionViewHolder>() {

    private var financialTransactions = arrayListOf<FinancialTransactionView>()

    fun swap(newPolicies: List<FinancialTransactionView>) {
        this.financialTransactions.clear()
        this.financialTransactions.addAll(newPolicies)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return financialTransactions.size
    }

    override fun onCreateViewHolder(parentView: ViewGroup, viewType: Int): FinancialTransactionViewHolder {
        val inflater = LayoutInflater.from(parentView.context)
        val binding =
            DataBindingUtil.inflate<ItemFinancialTransactionBinding>(inflater, R.layout.item_financial_transaction, parentView, false)

        return FinancialTransactionViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: FinancialTransactionViewHolder, position: Int) {
        viewHolder.onBind(financialTransactions[position], onItemSelected)
    }

    class FinancialTransactionViewHolder(private val binding: ItemFinancialTransactionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(transaction: FinancialTransactionView, onItemSelected: (FinancialTransactionView) -> Unit) {
            binding.transaction = transaction

            itemView.setOnClickListener {
                onItemSelected(transaction)
            }
        }
    }

}
