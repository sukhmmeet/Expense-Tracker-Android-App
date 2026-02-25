package com.dhaliwal.expensetracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Expense::class],
    version = 2
)
abstract class ExpensesDatabase : RoomDatabase() {
    abstract fun getDao() : ExpenseDao
}