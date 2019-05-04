package me.kevincampos.remote.model

class PolicyEventResponse(
    val unique_key: String,
    val type: String, // policy_created, policy_financial_transaction, policy_cancelled
    val timestamp: String,
    val payload: PayloadResponse
)

class PayloadResponse(
    val policy_id: String,

    // policy_created payload
    val user_id: String?,
    val user_revision: String?,
    val original_policy_id: String?,
    val reference_code: String?,
    val start_date: String?,
    val end_date: String?,
    val incident_phone: String?,

    val vehicle: VehicleResponse?,
    val documents: DocumentsResponse?,

    // policy_financial_transaction
    val pricing: PricingResponse?,

    // policy_cancelled payload
    val type: String?,
    val new_end_date: String?
)

class PricingResponse (
    val underwriter_premium: Double,
    val commission: Double,
    val total_premium: Double,
    val ipt: Double,
    val ipt_rate: Double,
    val extra_fees: Double,
    val vat: Double,
    val deductions: Double,
    val total_payable: Double
)

class VehicleResponse(
    val vrm: String,
    val prettyVrm: String,
    val make: String,
    val model: String,
    val color: String,
    val variant: String?
)

class DocumentsResponse(
    val certificate_url: String,
    val terms_url: String
)