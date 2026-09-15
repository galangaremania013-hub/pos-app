package com.workid.data.repositoryImpl

import com.workid.domain.model.*
import com.workid.domain.repository.EmployeeRepository
import com.workid.data.local.EmployeeDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val dataStore: EmployeeDataStore
) : EmployeeRepository {
    
    override fun getCurrentEmployee(): Flow<Employee?> = 
        dataStore.employeeName.map { name ->
            name?.let {
                Employee(
                    id = "EMP001",
                    name = it,
                    email = "budi.santoso@workid.com",
                    position = "Senior Developer",
                    department = "Engineering",
                    profileImageUrl = null,
                    joinDate = "2022-01-15",
                    employeeId = "EMP001"
                )
            }
        }
    
    override suspend fun getEmployeeById(id: String): Employee? {
        // Mock implementation
        return Employee(
            id = id,
            name = "Budi Santoso",
            email = "budi.santoso@workid.com",
            position = "Senior Developer",
            department = "Engineering",
            profileImageUrl = null,
            joinDate = "2022-01-15",
            employeeId = id
        )
    }
    
    override suspend fun updateProfile(employee: Employee): Result<Unit> {
        return try {
            dataStore.saveEmployeeInfo(
                id = employee.id,
                name = employee.name,
                email = employee.email,
                position = employee.position,
                department = employee.department,
                profileImage = employee.profileImageUrl
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
