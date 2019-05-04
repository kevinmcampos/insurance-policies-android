package me.kevincampos.domain.interactor

import me.kevincampos.domain.PolicyEventRepository
import me.kevincampos.domain.model.FinancialTransaction
import me.kevincampos.domain.util.Result
import javax.inject.Inject

class GetFinancialTransactionsByPolicyIdUseCase @Inject constructor(
    private val policyEventRepository: PolicyEventRepository
) {

    suspend operator fun invoke(policyId: String): Result<List<FinancialTransaction>> {
        return policyEventRepository.getFinancialTransactionsByPolicyId(policyId)
    }

}