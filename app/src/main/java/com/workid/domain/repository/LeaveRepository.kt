package com.workid.domain.repository

import com.workid.domain.model.LeaveBalance
import com.workid.domain.model.LeaveRequest
import kotlinx.coroutines.flow.Flow

interface LeaveRepository {
    fun getLeaveBalance(employeeId: String): Flow<LeaveBalance?>
    
    suspend fun submitLeaveRequest(leaveRequest: LeaveRequest): Result<String>
    
    suspend fun cancelLeaveRequest(leaveId: String): Result<Unit>
    
    fun getLeaveRequests(employeeId: String): Flow<List<LeaveRequest>>
    
    suspend fun getLeaveRequestById(leaveId: String): Result<LeaveRequest>
}
