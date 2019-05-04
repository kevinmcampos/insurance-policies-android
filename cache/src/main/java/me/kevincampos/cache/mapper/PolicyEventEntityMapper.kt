package me.kevincampos.cache.mapper

import me.kevincampos.cache.model.PolicyEventEntity
import me.kevincampos.domain.model.PolicyEvent
import javax.inject.Inject

class PolicyEventEntityMapper @Inject constructor() : EntityMapper<PolicyEventEntity, PolicyEvent> {

    override fun mapToEntity(domain: PolicyEvent): PolicyEventEntity {
        return PolicyEventEntity(domain.uniqueKey, domain.type, domain.timestamp, domain.policyId, domain.userId,
            domain.userRevision, domain.originalPolicyId, domain.referenceCode, domain.startDate, domain.endDate,
            domain.vrm, domain.prettyVrm, domain.make, domain.model, domain.color, domain.variant,
            domain.certificateUrl, domain.termsUrl, domain.underwriterPremium, domain.commission, domain.totalPremium,
            domain.ipt, domain.iptRate, domain.extraFees, domain.vat, domain.deductions, domain.totalPayable,
            domain.cancellationType, domain.newEndDate)
    }

    override fun mapFromEntity(entity: PolicyEventEntity): PolicyEvent {
        return PolicyEvent(entity.uniqueKey, entity.type, entity.timestamp, entity.policyId, entity.userId, 
            entity.userRevision, entity.originalPolicyId, entity.referenceCode, entity.startDate, entity.endDate,
            entity.vrm, entity.prettyVrm, entity.make, entity.model, entity.color, entity.variant, 
            entity.certificateUrl, entity.termsUrl, entity.underwriterPremium, entity.commission, entity.totalPremium,
            entity.ipt, entity.iptRate, entity.extraFees, entity.vat, entity.deductions, entity.totalPayable,
            entity.cancellationType, entity.newEndDate)
    }

}