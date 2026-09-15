package com.workid.data.repositoryImpl

import com.workid.domain.model.*
import com.workid.domain.repository.LeaveRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LeaveRepositoryImpl @Inject constructor() : LeaveRepository {
    
    override fun getLeaveBalance(employeeId: String): Flow<LeaveBalance?> = flow {
        kotlinx.coroutines.delay(300)
        
        emit(
            LeaveBalance(
                employeeId = employeeId,
                year = 2024,
                totalQuota = 12,
                used = 5,
                remaining = 7,
                expireDate = "2024-12-31"
            )
        )
    }
    
    override suspend fun submitLeaveRequest(request: LeaveRequest): Result<String> {
        return try {
            kotlinx.coroutines.delay(1000)
            // Mock successful submission
            Result.success("LEAVE-${System.currentTimeMillis()}")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getLeaveRequests(employeeId: String): List<LeaveRequest> {
        return listOf(
            LeaveRequest(
                id = "LV001",
                employeeId = employeeId,
                leaveType = LeaveType.ANNUAL,
                startDate = "2024-02-15",
                endDate = "2024-02-17",
                reason = "Family vacation",
                attachmentUrl = null,
                status = LeaveStatus.APPROVED,
                submittedDate = "2024-02-01",
                approvedBy = "Manager 1",
                approvedDate = "2024-02-03",
                rejectionReason = null
            ),
            LeaveRequest(
                id = "LV002",
                employeeId = employeeId,
                leaveType = LeaveType.SICK,
                startDate = "2024-03-10",
                endDate = "2024-03-11",
                reason = "Medical checkup",
                attachmentUrl = "https://example.com/medical_cert.pdf",
                status = LeaveStatus.APPROVED,
                submittedDate = "2024-03-08",
                approvedBy = "Manager 1",
                approvedDate = "2024-03-08",
                rejectionReason = null
            ),
            LeaveRequest(
                id = "LV003",
                employeeId = employeeId,
                leaveType = LeaveType.PERSONAL,
                startDate = "2024-04-20",
                endDate = "2024-04-20",
                reason = "Personal matters",
                attachmentUrl = null,
                status = LeaveStatus.PENDING,
                submittedDate = "2024-04-15",
                approvedBy = null,
                approvedDate = null,
                rejectionReason = null
            )
        )
    }
    
    override suspend fun cancelLeaveRequest(requestId: String): Result<Unit> {
        return try {
            kotlinx.coroutines.delay(500)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
