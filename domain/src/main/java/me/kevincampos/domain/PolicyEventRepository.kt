package me.kevincampos.domain

import me.kevincampos.domain.model.FinancialTransaction
import me.kevincampos.domain.model.VehicleWithPolicies
import me.kevincampos.domain.util.Result

interface PolicyEventRepository {

    suspend fun synchronizePolicyEvents(): Result<Boolean>

    suspend fun getAllVehiclesWithPolicies(): Result<List<VehicleWithPolicies>>

    suspend fun getVehicleByVrm(vrm: String): Result<VehicleWithPolicies>

    suspend fun getFinancialTransactionsByPolicyId(policyId: String): Result<List<FinancialTransaction>>

}