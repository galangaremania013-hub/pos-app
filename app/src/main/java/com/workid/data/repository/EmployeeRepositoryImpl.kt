package com.workid.data.repository

import com.workid.domain.model.Employee
import com.workid.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeRepositoryImpl @Inject constructor() : EmployeeRepository {
    
    private var currentEmployee: Employee? = Employee(
        id = "emp_001",
        name = "Budi Santoso",
        email = "budi.santoso@workid.com",
        position = "Senior Developer",
        department = "Engineering",
        profileImageUrl = null,
        joinDate = System.currentTimeMillis() - 31536000000, // 1 year ago
        employeeCode = "WRK-2023-001"
    )
    
    override fun getCurrentEmployee(): Flow<Employee?> = flow {
        kotlinx.coroutines.delay(300)
        emit(currentEmployee)
    }
    
    override suspend fun getEmployeeById(id: String): Result<Employee> {
        return Result.success(currentEmployee!!)
    }
    
    override suspend fun updateEmployeeProfile(employee: Employee): Result<Unit> {
        return try {
            currentEmployee = employee
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override fun getAllEmployees(): Flow<List<Employee>> = flow {
        kotlinx.coroutines.delay(500)
        emit(listOf(currentEmployee!!))
    }
}
