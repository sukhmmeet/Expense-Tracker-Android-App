package com.dhaliwal.expensetracker.data.Util

import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.data.local.ExpensesConstants
import com.dhaliwal.expensetracker.data.local.TotalAmount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Locale.getDefault

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

    //type = if(selectedTypeIncomeOrExpense == TransactionType.EXPENSE){
    //                                "Expense"
    //                            } else {
    //                                "Income"
    //                            }
    fun getTotalExpenseAndIncome(expenses: Flow<List<Expense>>): Flow<TotalAmount> {
        return expenses.map { expenseList ->

            var expenseTotal = 0.0
            var incomeTotal = 0.0

            expenseList.forEach { expense ->
                if (expense.type.lowercase(getDefault()) == "expense") {
                    expenseTotal += expense.amount
                } else {
                    incomeTotal += expense.amount
                }
            }

            TotalAmount(expenseTotal, incomeTotal)
        }
    }
}