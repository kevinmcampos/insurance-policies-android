package me.kevincampos.presentation.model

import android.content.Context
import me.kevincampos.presentation.R
import java.text.DecimalFormat

class FinancialTransactionView(
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

    private val decimalFormat = DecimalFormat("###.##")

    fun getReadableTotalPayable(context: Context): String {
        return context.getString(R.string.money_format, (decimalFormat.format(totalPayable)))
    }

}