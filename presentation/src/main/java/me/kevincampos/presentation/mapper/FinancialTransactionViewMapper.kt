package me.kevincampos.presentation.mapper

import me.kevincampos.domain.model.FinancialTransaction
import me.kevincampos.presentation.model.FinancialTransactionView
import javax.inject.Inject

class FinancialTransactionViewMapper @Inject constructor() : ViewMapper<FinancialTransactionView, FinancialTransaction> {

    override fun mapToView(domain: FinancialTransaction): FinancialTransactionView {
        return FinancialTransactionView(domain.underwriterPremium, domain.commission, domain.totalPremium, domain.ipt,
            domain.iptRate, domain.extraFees, domain.vat, domain.deductions, domain.totalPayable)
    }

}