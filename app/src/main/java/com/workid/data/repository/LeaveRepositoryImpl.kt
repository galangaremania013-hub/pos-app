package com.workid.data.repository

import com.workid.domain.model.LeaveBalance
import com.workid.domain.model.LeaveRequest
import com.workid.domain.model.LeaveStatus
import com.workid.domain.model.LeaveType
import com.workid.domain.repository.LeaveRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LeaveRepositoryImpl @Inject constructor() : LeaveRepository {
    
    private val mockLeaveBalance = LeaveBalance(
        annualLeaveTotal = 12,
        annualLeaveUsed = 3,
        annualLeaveRemaining = 9,
        sickLeaveRemaining = 10,
        personalLeaveRemaining = 3
    )
    
    private val mockLeaveRequests = listOf(
        LeaveRequest(
            id = "leave_001",
            employeeId = "emp_001",
            leaveType = LeaveType.ANNUAL,
            startDate = System.currentTimeMillis() - 86400000 * 5,
            endDate = System.currentTimeMillis() - 86400000 * 3,
            reason = "Liburan keluarga",
            documentUrl = null,
            status = LeaveStatus.APPROVED,
            submittedDate = System.currentTimeMillis() - 86400000 * 10,
            reviewedDate = System.currentTimeMillis() - 86400000 * 8
        ),
        LeaveRequest(
            id = "leave_002",
            employeeId = "emp_001",
            leaveType = LeaveType.SICK,
            startDate = System.currentTimeMillis() - 86400000 * 2,
            endDate = System.currentTimeMillis() - 86400000 * 2,
            reason = "Demam",
            documentUrl = null,
            status = LeaveStatus.PENDING,
            submittedDate = System.currentTimeMillis() - 86400000 * 2
        )
    )
    
    override fun getLeaveBalance(employeeId: String): Flow<LeaveBalance?> = flow {
        kotlinx.coroutines.delay(300)
        emit(mockLeaveBalance)
    }
    
    override suspend fun submitLeaveRequest(leaveRequest: LeaveRequest): Result<String> {
        return try {
            val newId = "leave_${System.currentTimeMillis()}"
            // In real implementation, send to API
            Result.success(newId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun cancelLeaveRequest(leaveId: String): Result<Unit> {
        return Result.success(Unit)
    }
    
    override fun getLeaveRequests(employeeId: String): Flow<List<LeaveRequest>> = flow {
        kotlinx.coroutines.delay(300)
        emit(mockLeaveRequests.filter { it.employeeId == employeeId })
    }
    
    override suspend fun getLeaveRequestById(leaveId: String): Result<LeaveRequest> {
        return Result.success(mockLeaveRequests.firstOrNull { it.id == leaveId } 
            ?: throw Exception("Leave request not found"))
    }
}
