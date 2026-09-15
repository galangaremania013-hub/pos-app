package com.workid.domain.usecase

import com.workid.domain.model.Employee
import com.workid.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentEmployeeUseCase @Inject constructor(
    private val employeeRepository: EmployeeRepository
) {
    operator fun invoke(): Flow<Employee?> {
        return employeeRepository.getCurrentEmployee()
    }
}

class GetEmployeeByIdUseCase @Inject constructor(
    private val employeeRepository: EmployeeRepository
) {
    suspend operator fun invoke(id: String): Result<Employee> {
        return employeeRepository.getEmployeeById(id)
    }
}
