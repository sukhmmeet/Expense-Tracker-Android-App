package com.dhaliwal.expensetracker.DatabaseClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,        // NOT NULL
    val amount: Double,       // NOT NULL
    val category: String,     // NOT NULL
    val date: Long,           // NOT NULL
    val note: String = "",    // NOT NULL, default empty string
    val type: String,         // NOT NULL ("Income" or "Expense")

    val isRecurring: Boolean = false,  // NOT NULL, default false
    val tags: String = ""               // NOT NULL, default empty string
)
