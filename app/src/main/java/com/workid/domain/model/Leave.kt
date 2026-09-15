package com.workid.domain.model

data class LeaveRequest(
    val id: String,
    val employeeId: String,
    val leaveType: LeaveType,
    val startDate: Long,
    val endDate: Long,
    val reason: String,
    val documentUrl: String?,
    val status: LeaveStatus,
    val submittedDate: Long,
    val reviewedDate: Long?,
    val reviewerNote: String? = null
)

enum class LeaveType {
    ANNUAL,
    SICK,
    PERSONAL,
    MATERNITY,
    PATERNITY,
    UNPAID,
    OTHER
}

enum class LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED
}

data class LeaveBalance(
    val annualLeaveTotal: Int,
    val annualLeaveUsed: Int,
    val annualLeaveRemaining: Int,
    val sickLeaveRemaining: Int,
    val personalLeaveRemaining: Int
) {
    val annualLeavePercentage: Float
        get() = if (annualLeaveTotal > 0) {
            (annualLeaveUsed.toFloat() / annualLeaveTotal) * 100
        } else 0f
}
