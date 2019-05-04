package me.kevincampos.remote

import me.kevincampos.data.remote.PolicyEventRemote
import me.kevincampos.domain.model.PolicyEvent
import me.kevincampos.domain.util.Result
import me.kevincampos.domain.util.safeApiCall
import me.kevincampos.remote.mapper.PolicyEventResponseMapper
import me.kevincampos.remote.service.PolicyEventService
import java.io.IOException
import javax.inject.Inject

class PolicyEventRemoteImpl @Inject constructor(
    private val service: PolicyEventService,
    private val mapper: PolicyEventResponseMapper
) : PolicyEventRemote {

    override suspend fun fetchPolicyEvents() = safeApiCall(
        call = { requestFetchPolicyEvents() },
        errorMessage = "Failed to fetch events from remote"
    )

    private suspend fun requestFetchPolicyEvents() : Result<List<PolicyEvent>> {
        val response = service.fetchPolicyEventsAsync().await()

        return if (response.isSuccessful) {
            val policyEventList = response.body()?.map {
                mapper.mapToDomain(it)
            } ?: listOf()

            Result.Success(policyEventList)
        } else {
            Result.Error(IOException("Failed to fetch events from remote"))
        }
    }

}