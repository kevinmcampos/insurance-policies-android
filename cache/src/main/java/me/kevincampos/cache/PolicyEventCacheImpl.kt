package me.kevincampos.cache

import me.kevincampos.cache.database.InsurancePoliciesDatabase
import me.kevincampos.cache.mapper.PolicyEventEntityMapper
import me.kevincampos.data.cache.PolicyEventCache
import me.kevincampos.domain.model.PolicyEvent
import me.kevincampos.domain.util.Result
import javax.inject.Inject

class PolicyEventCacheImpl @Inject constructor(
    private val database: InsurancePoliciesDatabase,
    private val mapper: PolicyEventEntityMapper
) : PolicyEventCache {

    override suspend fun getPolicyEvents(): Result<List<PolicyEvent>> {
        val policyEventEntities = database.policyEventDao().getPolicyEvents()
        val policyEvents = policyEventEntities.map { mapper.mapFromEntity(it) }
        return Result.Success(policyEvents)
    }

    override suspend fun getPolicyEventsWith(policyId: String, type: String): Result<List<PolicyEvent>> {
        val policyEventEntities = database.policyEventDao().getPolicyEventsWith(policyId = policyId, type = type)
        val policyEvents = policyEventEntities.map { mapper.mapFromEntity(it) }
        return Result.Success(policyEvents)
    }

    override suspend fun insertPolicyEvents(policyEvents: List<PolicyEvent>): Result<Boolean> {
        database.policyEventDao().insertPolicyEvents(
            policyEvents.map { mapper.mapToEntity(it) }
        )
        return Result.Success(true)
    }

}