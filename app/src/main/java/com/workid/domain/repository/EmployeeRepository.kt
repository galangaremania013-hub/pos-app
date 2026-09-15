package com.workid.domain.repository

import com.workid.domain.model.Employee
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    fun getCurrentEmployee(): Flow<Employee?>
    
    suspend fun getEmployeeById(id: String): Result<Employee>
    
    suspend fun updateEmployeeProfile(employee: Employee): Result<Unit>
    
    fun getAllEmployees(): Flow<List<Employee>>
}
