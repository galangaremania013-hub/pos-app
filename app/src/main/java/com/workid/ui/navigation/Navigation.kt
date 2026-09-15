package com.workid.ui.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Attendance : Screen("attendance")
    object Payroll : Screen("payroll")
    object Leave : Screen("leave")
    object History : Screen("history")
    object Approval : Screen("approval")
    object Profile : Screen("profile")
    object PayslipDetail : Screen("payroll/payslip/{payslipId}") {
        fun createRoute(payslipId: String) = "payroll/payslip/$payslipId"
    }
}

val bottomNavItems = listOf(
    Screen.Dashboard,
    Screen.History,
    Screen.Approval,
    Screen.Profile
)
