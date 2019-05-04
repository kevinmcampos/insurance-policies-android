package me.kevincampos.presentation.model

import android.content.Context
import android.graphics.drawable.Drawable
import me.kevincampos.presentation.R

class VehicleWithPoliciesView(
    val prettyVrm: String,
    val vrm: String,
    val make: String,
    val model: String,
    val color: String,
    val policies: List<PolicyView>
): VehicleListItem {

    override val id: String = prettyVrm

    override fun isHeader() = false

    val policiesCount: String = policies.size.toString()

    val hasActivePolicy: Boolean = policies.any { it.isActive }

    val hasInactivePolicy: Boolean = policies.any { !it.isActive }

    val readableRemainTime: String? = policies.find { it.isActive }?.readableRemainTime

    fun getMakeIcon(context: Context): Drawable {
        return when (make) {
            "Mercedes-Benz" -> context.resources.getDrawable(R.drawable.carlogo_mercedes)
            "MINI" -> context.resources.getDrawable(R.drawable.carlogo_mini)
            "Volkswagen" -> context.resources.getDrawable(R.drawable.carlogo_volkswagen)
            // TODO: Add a placeholder
            else -> context.resources.getDrawable(R.drawable.carlogo_volkswagen)
        }
    }

}