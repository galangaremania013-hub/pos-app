package com.workid.data.repositoryImpl

import com.workid.domain.model.*
import com.workid.domain.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor() : TaskRepository {
    
    override suspend fun submitDailyReport(report: DailyTaskReport): Result<String> {
        return try {
            kotlinx.coroutines.delay(800)
            Result.success("TASK-${System.currentTimeMillis()}")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getDailyReports(
        employeeId: String,
        month: Int,
        year: Int
    ): List<DailyTaskReport> {
        return listOf(
            DailyTaskReport(
                id = "TR001",
                employeeId = employeeId,
                date = "2024-03-18",
                tasks = listOf(
                    TaskItem("Implement QR Scanner feature", TaskStatus.DONE, "2024-03-18 15:00"),
                    TaskItem("Fix attendance bug", TaskStatus.DONE, "2024-03-18 12:00"),
                    TaskItem("Code review for team", TaskStatus.IN_PROGRESS, null)
                ),
                accomplishments = "Completed QR scanner integration with ML Kit. Fixed geofencing validation issue.",
                challenges = "Camera permission handling on Android 13+",
                plans = "Continue with biometric authentication implementation",
                submittedDate = "2024-03-18 17:30"
            ),
            DailyTaskReport(
                id = "TR002",
                employeeId = employeeId,
                date = "2024-03-17",
                tasks = listOf(
                    TaskItem("Design database schema", TaskStatus.DONE, "2024-03-17 14:00"),
                    TaskItem("Setup Hilt dependency injection", TaskStatus.DONE, "2024-03-17 16:00")
                ),
                accomplishments = "Completed Clean Architecture setup. All repositories and use cases implemented.",
                challenges = null,
                plans = "Start working on QR scanner module",
                submittedDate = "2024-03-17 17:00"
            )
        )
    }
}
