package me.kevincampos.domain.model


class Policy(
    val policyId: String,
    val userId: String?,
    val userRevision: String?,
    val originalPolicyId: String?,
    val referenceCode: String?,
    val startDate: String?,
    val endDate: String?,
    val certificateUrl: String?,
    val termsUrl: String?,
    val cancellationType: String?,
    val newEndDate: String?,
    val financialTransactions: List<FinancialTransaction>,

    val vrm: String?,
    val prettyVrm: String?,
    val make: String?,
    val model: String?,
    val color: String?,
    val variant: String?
) {

    companion object {

        fun fromPolicyEvents(policiesEvents: List<PolicyEvent>): Policy {
            val creationEvents = policiesEvents.filter { it.type == "policy_created" }
            if (creationEvents.isEmpty()) throw IllegalStateException("Policy id without policy_created event")
            if (creationEvents.size > 1) throw IllegalStateException("Policy id with more than one policy_created event")

            val creationEvent = creationEvents[0]

            val transactionEvents = policiesEvents.filter { it.type == "policy_financial_transaction" }
            val financialTransactions = transactionEvents.map { FinancialTransaction.fromFinancialTransactionEvent(it) }

            val policyCancellationEvents = policiesEvents.filter { it.type == "policy_cancelled" }
            val policyCancellationEvent = policyCancellationEvents.getOrNull(0)

            return Policy(
                creationEvent.policyId,
                creationEvent.userId,
                creationEvent.userRevision,
                creationEvent.originalPolicyId,
                creationEvent.referenceCode,
                creationEvent.startDate,
                creationEvent.endDate,
                creationEvent.certificateUrl,
                creationEvent.termsUrl,
                policyCancellationEvent?.cancellationType,
                policyCancellationEvent?.newEndDate,
                financialTransactions,
                creationEvent.vrm,
                creationEvent.prettyVrm,
                creationEvent.make,
                creationEvent.model,
                creationEvent.color,
                creationEvent.variant
            )
        }

    }

}