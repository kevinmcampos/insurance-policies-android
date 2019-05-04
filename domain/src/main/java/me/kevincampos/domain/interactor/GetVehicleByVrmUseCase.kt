package me.kevincampos.domain.interactor

import me.kevincampos.domain.PolicyEventRepository
import me.kevincampos.domain.model.VehicleWithPolicies
import me.kevincampos.domain.util.Result
import javax.inject.Inject

class GetVehicleByVrmUseCase @Inject constructor(
    private val policyEventRepository: PolicyEventRepository
) {

    suspend operator fun invoke(vrm: String): Result<VehicleWithPolicies> {
        return policyEventRepository.getVehicleByVrm(vrm)
    }

}