package me.kevincampos.data

import me.kevincampos.data.cache.PolicyEventCache
import me.kevincampos.data.remote.PolicyEventRemote
import me.kevincampos.domain.PolicyEventRepository
import me.kevincampos.domain.model.FinancialTransaction
import me.kevincampos.domain.model.Policy
import me.kevincampos.domain.model.PolicyEvent
import me.kevincampos.domain.model.VehicleWithPolicies
import me.kevincampos.domain.util.Result
import java.io.IOException
import javax.inject.Inject

class PolicyEventEventRepositoryImpl @Inject constructor(
    private val policyEventCache: PolicyEventCache,
    private val policyEventRemote: PolicyEventRemote
) : PolicyEventRepository {

    override suspend fun synchronizePolicyEvents(): Result<Boolean> {
        val remoteResult = policyEventRemote.fetchPolicyEvents()

        if (remoteResult is Result.Success) {
            policyEventCache.insertPolicyEvents(remoteResult.data)
            return Result.Success(true)
        } else {
            return Result.Error(IOException("Failed to fetch events"))
        }
    }

    override suspend fun getAllVehiclesWithPolicies(): Result<List<VehicleWithPolicies>> {
        val cacheResult = policyEventCache.getPolicyEvents()

        if (cacheResult is Result.Success) {
            val policyEvents: List<PolicyEvent> = cacheResult.data
            // Group event by policy id
            val policyEventsById = policyEvents.groupBy { it.policyId }

            // Create policies from events
            val policies: List<Policy> = policyEventsById.map {
                Policy.fromPolicyEvents(it.value)
            }

            // Group policies into vehicles
            val policiesByVehicle = policies.groupBy { it.vrm }

            // Create vehicles from policies
            val vehiclesWithPolicies: List<VehicleWithPolicies> = policiesByVehicle.map {
                VehicleWithPolicies.from(it.value)
            }

            return Result.Success(vehiclesWithPolicies)
        }

        return Result.Error(IOException())
    }

    override suspend fun getVehicleByVrm(vrm: String): Result<VehicleWithPolicies> {
        val cacheResult = policyEventCache.getPolicyEvents()

        if (cacheResult is Result.Success) {
            val policyEvents = cacheResult.data
            // Group event by policy id
            val policyEventsById = policyEvents.groupBy { it.policyId }

            // Create policies from events
            val policies = policyEventsById.map {
                Policy.fromPolicyEvents(it.value)
            }

            // Keep the policies with the desired 'vrm'
            val filteredPolicies = policies.filter { it.vrm == vrm }
            if (filteredPolicies.isEmpty()) return Result.Error(IOException("Vehicle with vrm $vrm does not exist"))

            // Create the vehicle with policies
            val vehicleWithPolicies = VehicleWithPolicies.from(filteredPolicies)

            return Result.Success(vehicleWithPolicies)
        }

        return Result.Error(IOException())
    }

    override suspend fun getFinancialTransactionsByPolicyId(policyId: String): Result<List<FinancialTransaction>> {
        val cacheResult = policyEventCache.getPolicyEventsWith(policyId = policyId, type = "policy_financial_transaction")

        if (cacheResult is Result.Success) {
            val financialTransactions = cacheResult.data.map {
                FinancialTransaction.fromFinancialTransactionEvent(it)
            }
            return Result.Success(financialTransactions)
        }

        return Result.Error(IOException())
    }

}