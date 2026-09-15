package com.workid.domain.model

data class OvertimeClaim(
    val id: String,
    val employeeId: String,
    val date: Long,
    val startTime: Long,
    val endTime: Long,
    val totalHours: Double,
    val reason: String,
    val status: OvertimeStatus,
    val submittedDate: Long,
    val approvedDate: Long?,
    val approverNote: String? = null
)

enum class OvertimeStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED
}
