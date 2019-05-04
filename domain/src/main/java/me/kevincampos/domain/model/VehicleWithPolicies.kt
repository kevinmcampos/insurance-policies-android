package me.kevincampos.domain.model

class VehicleWithPolicies(
    val vrm: String,
    val prettyVrm: String,
    val make: String,
    val model: String,
    val color: String,
    val variant: String?,
    val policies: List<Policy>
) {

    companion object {

        fun from(policies: List<Policy>): VehicleWithPolicies {
            val firstPolicy = policies[0]

            return VehicleWithPolicies(
                firstPolicy.vrm ?: throw IllegalStateException("Policy without vehicle vrm"),
                firstPolicy.prettyVrm ?: throw IllegalStateException("Policy without vehicle prettyVrm"),
                firstPolicy.make ?: throw IllegalStateException("Policy without vehicle make"),
                firstPolicy.model ?: throw IllegalStateException("Policy without vehicle model"),
                firstPolicy.color ?: throw IllegalStateException("Policy without vehicle color"),
                firstPolicy.variant,
                policies
            )
        }

    }

}