package me.kevincampos.presentation.mapper

import me.kevincampos.domain.model.Policy
import me.kevincampos.presentation.model.PolicyView
import javax.inject.Inject

class PolicyViewMapper @Inject constructor() : ViewMapper<PolicyView, Policy> {

    override fun mapToView(domain: Policy): PolicyView {
        return PolicyView(domain.policyId, domain.originalPolicyId, domain.startDate, domain.endDate,
            domain.certificateUrl, domain.termsUrl, domain.cancellationType, domain.newEndDate)
    }

}