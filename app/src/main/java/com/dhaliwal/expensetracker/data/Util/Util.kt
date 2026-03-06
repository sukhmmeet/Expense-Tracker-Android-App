package com.dhaliwal.expensetracker.data.Util

import com.dhaliwal.expensetracker.R
import com.dhaliwal.expensetracker.data.local.ExpensesConstants
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.ExpenseDonutChart
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Util {
    fun convertMillisToDate(
        millis: Long?,
        pattern: String = "dd MMM yyyy"
    ): String {

        if (millis == null) return ""

        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(Date(millis))
    }
    fun getLogo(category : String) : Int {
        var index = 0
        while (index < ExpensesConstants.category.size){
            if (category == ExpensesConstants.category[index]){
                break
            }
            index++
        }
        return ExpensesConstants.categoryIcon[index]
    }
}