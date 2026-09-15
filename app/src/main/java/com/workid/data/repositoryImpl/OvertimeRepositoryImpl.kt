package com.workid.data.repositoryImpl

import com.workid.domain.model.*
import com.workid.domain.repository.OvertimeRepository
import javax.inject.Inject

class OvertimeRepositoryImpl @Inject constructor() : OvertimeRepository {
    
    override suspend fun submitOvertimeClaim(claim: OvertimeClaim): Result<String> {
        return try {
            kotlinx.coroutines.delay(1000)
            Result.success("OT-${System.currentTimeMillis()}")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getOvertimeClaims(employeeId: String): List<OvertimeClaim> {
        return listOf(
            OvertimeClaim(
                id = "OT001",
                employeeId = employeeId,
                date = "2024-03-15",
                startTime = "18:00",
                endTime = "21:00",
                totalHours = 3.0,
                reason = "Project deadline - Sprint completion",
                status = ApprovalStatus.APPROVED,
                submittedDate = "2024-03-16",
                approvedBy = "Manager 1",
                notes = "Approved for project delivery"
            ),
            OvertimeClaim(
                id = "OT002",
                employeeId = employeeId,
                date = "2024-03-18",
                startTime = "17:30",
                endTime = "20:00",
                totalHours = 2.5,
                reason = "Bug fixing and deployment",
                status = ApprovalStatus.PENDING,
                submittedDate = "2024-03-19",
                approvedBy = null,
                notes = null
            ),
            OvertimeClaim(
                id = "OT003",
                employeeId = employeeId,
                date = "2024-03-10",
                startTime = "18:00",
                endTime = "22:00",
                totalHours = 4.0,
                reason = "Production issue resolution",
                status = ApprovalStatus.REJECTED,
                submittedDate = "2024-03-11",
                approvedBy = "Manager 1",
                notes = "Please provide more details on the production issue"
            )
        )
    }
    
    override suspend fun approveOvertimeClaim(claimId: String, approvedBy: String): Result<Unit> {
        return try {
            kotlinx.coroutines.delay(500)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
