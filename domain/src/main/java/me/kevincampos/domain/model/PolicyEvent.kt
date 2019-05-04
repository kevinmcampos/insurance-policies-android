package me.kevincampos.domain.model

class PolicyEvent(
    val uniqueKey: String,
    val type: String, // policy_created, policy_financial_transaction, policy_cancelled
    val timestamp: String,
    val policyId: String,

    // policy_created payload
    val userId: String?,
    val userRevision: String?,
    val originalPolicyId: String?,
    val referenceCode: String?,
    val startDate: String?,
    val endDate: String?,
    // vehicle
    val vrm: String?,
    val prettyVrm: String?,
    val make: String?,
    val model: String?,
    val color: String?,
    val variant: String?,
    // documents
    val certificateUrl: String?,
    val termsUrl: String?,

    // policy_financial_transaction payload
    val underwriterPremium: Double?,
    val commission: Double?,
    val totalPremium: Double?,
    val ipt: Double?,
    val iptRate: Double?,
    val extraFees: Double?,
    val vat: Double?,
    val deductions: Double?,
    val totalPayable: Double?,

    // policy_cancelled payload
    val cancellationType: String?,
    val newEndDate: String?
)