package com.dhaliwal.expensetracker.data.local

import androidx.compose.ui.graphics.Color
import com.dhaliwal.expensetracker.R

object ExpensesConstants {
    val category = listOf(
        "Food",           // 0
        "Transport",      // 1
        "Bills",          // 2
        "Rent",           // 3
        "Entertainment",  // 4
        "Health",         // 5
        "Shopping",       // 6
        "Education",      // 7
        "Investment",     // 8
        "Salary",         // 9
        "Others"          // 10
    )
    val categoryIcon = listOf(
        R.drawable.food,
        R.drawable.transport,
        R.drawable.bill,
        R.drawable.rent,
        R.drawable.entertainment,
        R.drawable.health,
        R.drawable.shopping,
        R.drawable.education,
        R.drawable.investment,
        R.drawable.salary,
        R.drawable.other
    )
    val tag = listOf(
        "All",   // 0
        "Daily",      // 1
        "Weekly",     // 2
        "Monthly",    // 3
        "Yearly",     // 4
        "Occasional", // 5
        "Long Term",  // 6
        "One Time"    //7
    )
    val payment_method = listOf(
        "Cash",           // 0
        "UPI",            // 1
        "Debit Card",     // 2
        "Credit Card",    // 3
        "Bank Transfer",  // 4
        "Net Banking",    // 5
        "Wallet",         // 6
        "Investment"      // 7
    )
}