package me.kevincampos.domain.interactor

import me.kevincampos.domain.PolicyEventRepository
import me.kevincampos.domain.model.VehicleWithPolicies
import me.kevincampos.domain.util.Result
import javax.inject.Inject

class GetAllVehiclesUseCase @Inject constructor(
    private val policyEventRepository: PolicyEventRepository
) {

    suspend operator fun invoke(): Result<List<VehicleWithPolicies>> {
        return policyEventRepository.getAllVehiclesWithPolicies()
    }

}