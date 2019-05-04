package me.kevincampos.domain.interactor

import me.kevincampos.domain.PolicyEventRepository
import me.kevincampos.domain.util.Result
import javax.inject.Inject

class SynchronizePolicyEventsUseCase @Inject constructor(
    private val policyEventRepository: PolicyEventRepository
) {

    suspend operator fun invoke(): Result<Boolean> {
        return policyEventRepository.synchronizePolicyEvents()
    }

}