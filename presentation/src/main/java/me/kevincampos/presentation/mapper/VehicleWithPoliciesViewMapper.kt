package me.kevincampos.presentation.mapper

import me.kevincampos.domain.model.VehicleWithPolicies
import me.kevincampos.presentation.model.VehicleWithPoliciesView
import javax.inject.Inject

class VehicleWithPoliciesViewMapper @Inject constructor(
    private val policyMapper: PolicyViewMapper
) : ViewMapper<VehicleWithPoliciesView, VehicleWithPolicies> {

    override fun mapToView(domain: VehicleWithPolicies): VehicleWithPoliciesView {
        return VehicleWithPoliciesView(domain.prettyVrm, domain.vrm, domain.make, domain.model, domain.color,
            domain.policies.map { policyMapper.mapToView(it) })
    }

}