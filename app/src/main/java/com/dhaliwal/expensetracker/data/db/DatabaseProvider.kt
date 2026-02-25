package com.dhaliwal.expensetracker.data.db

import android.content.Context
import androidx.room.Room
import com.dhaliwal.expensetracker.data.local.ExpensesDatabase

object DatabaseProvider {

    @Volatile
    private var INSTANCE : ExpensesDatabase? = null

    fun getDatabase(context : Context) : ExpensesDatabase {
        return (INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ExpensesDatabase::class.java,
                "database/expenses.db"
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            instance
        })
    }
}