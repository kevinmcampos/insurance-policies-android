package me.kevincampos.data.cache

import me.kevincampos.domain.model.PolicyEvent
import me.kevincampos.domain.util.Result

interface PolicyEventCache {

    suspend fun getPolicyEvents(): Result<List<PolicyEvent>>

    suspend fun getPolicyEventsWith(policyId: String, type: String): Result<List<PolicyEvent>>

    suspend fun insertPolicyEvents(policyEvents: List<PolicyEvent>): Result<Boolean>

}