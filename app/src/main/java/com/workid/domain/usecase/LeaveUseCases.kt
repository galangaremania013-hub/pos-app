package com.workid.domain.usecase

import com.workid.domain.model.LeaveBalance
import com.workid.domain.model.LeaveRequest
import com.workid.domain.repository.LeaveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLeaveBalanceUseCase @Inject constructor(
    private val leaveRepository: LeaveRepository
) {
    operator fun invoke(employeeId: String): Flow<LeaveBalance?> {
        return leaveRepository.getLeaveBalance(employeeId)
    }
}

class SubmitLeaveRequestUseCase @Inject constructor(
    private val leaveRepository: LeaveRepository
) {
    suspend operator fun invoke(leaveRequest: LeaveRequest): Result<String> {
        return leaveRepository.submitLeaveRequest(leaveRequest)
    }
}

class GetLeaveRequestsUseCase @Inject constructor(
    private val leaveRepository: LeaveRepository
) {
    operator fun invoke(employeeId: String): Flow<List<LeaveRequest>> {
        return leaveRepository.getLeaveRequests(employeeId)
    }
}
