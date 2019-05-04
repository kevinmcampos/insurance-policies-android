package me.kevincampos.remote.mapper

import me.kevincampos.domain.model.PolicyEvent
import me.kevincampos.remote.model.PolicyEventResponse
import javax.inject.Inject

class PolicyEventResponseMapper @Inject constructor():
    ResponseMapper<PolicyEventResponse, PolicyEvent> {

    override fun mapToDomain(response: PolicyEventResponse): PolicyEvent {
        return PolicyEvent(response.unique_key, response.type, response.timestamp, response.payload.policy_id,
            response.payload.user_id, response.payload.user_revision, response.payload.original_policy_id,
            response.payload.reference_code, response.payload.start_date, response.payload.end_date,
            response.payload.vehicle?.vrm, response.payload.vehicle?.prettyVrm, response.payload.vehicle?.make,
            response.payload.vehicle?.model, response.payload.vehicle?.color, response.payload.vehicle?.variant,
            response.payload.documents?.certificate_url, response.payload.documents?.terms_url,
            response.payload.pricing?.underwriter_premium, response.payload.pricing?.commission,
            response.payload.pricing?.total_premium, response.payload.pricing?.ipt, response.payload.pricing?.ipt_rate,
            response.payload.pricing?.extra_fees, response.payload.pricing?.vat, response.payload.pricing?.deductions,
            response.payload.pricing?.total_payable, response.payload.type, response.payload.new_end_date)
    }

}