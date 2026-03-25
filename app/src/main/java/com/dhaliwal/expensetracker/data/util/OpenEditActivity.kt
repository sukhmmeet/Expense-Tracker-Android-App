package com.dhaliwal.expensetracker.data.util

import android.content.Context
import android.content.Intent
import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.presentation.app_ui.AddExpenseActivity

fun openEditActivity(
    context : Context,
    expense : Expense
){
    val intent = Intent(context, AddExpenseActivity::class.java)

    intent.putExtra("id", expense.id)
    intent.putExtra("title", expense.title)
    intent.putExtra("amount", expense.amount)
    intent.putExtra("category", expense.category)
    intent.putExtra("date", expense.date)
    intent.putExtra("note", expense.note)
    intent.putExtra("type", expense.type)
    intent.putExtra("isRecurring", expense.isRecurring)
    intent.putExtra("tags", expense.tags)
    intent.putExtra("payment_method", expense.payment_method)

    context.startActivity(intent)
}