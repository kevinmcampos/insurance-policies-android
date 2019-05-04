package me.kevincampos.data.remote

import me.kevincampos.domain.model.PolicyEvent
import me.kevincampos.domain.util.Result

interface PolicyEventRemote {

    suspend fun fetchPolicyEvents(): Result<List<PolicyEvent>>

}