package me.kevincampos.domain.model

class FinancialTransaction(
    val underwriterPremium: Double?,
    val commission: Double?,
    val totalPremium: Double?,
    val ipt: Double?,
    val iptRate: Double?,
    val extraFees: Double?,
    val vat: Double?,
    val deductions: Double?,
    val totalPayable: Double?
) {

    companion object {

        fun fromFinancialTransactionEvent(transactionPolicyEvent: PolicyEvent): FinancialTransaction {
            return FinancialTransaction(
                transactionPolicyEvent.underwriterPremium,
                transactionPolicyEvent.commission,
                transactionPolicyEvent.totalPremium,
                transactionPolicyEvent.ipt,
                transactionPolicyEvent.iptRate,
                transactionPolicyEvent.extraFees,
                transactionPolicyEvent.vat,
                transactionPolicyEvent.deductions,
                transactionPolicyEvent.totalPayable
            )
        }

    }

}