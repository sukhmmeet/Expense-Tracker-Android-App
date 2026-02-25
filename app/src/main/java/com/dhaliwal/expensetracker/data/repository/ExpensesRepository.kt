package com.dhaliwal.expensetracker.data.repository

import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.data.local.ExpenseDao
import kotlinx.coroutines.flow.Flow

class ExpensesRepository(private val expenseDao: ExpenseDao) {

    // ------------------ Modify Data ------------------

    suspend fun insert(expense: Expense) {
        expenseDao.insertExpense(expense)
    }

    suspend fun update(expense: Expense) {
        expenseDao.updateExpense(expense)
    }

    suspend fun delete(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }

    // ------------------ Read Data ------------------

    fun getAllExpenses(): Flow<List<Expense>> = expenseDao.getAllExpenses()

    fun getOnlyExpenses(): Flow<List<Expense>> = expenseDao.getOnlyExpenses()

    fun getOnlyIncomes(): Flow<List<Expense>> = expenseDao.getOnlyIncomes()

    fun getByCategory(category: String): Flow<List<Expense>> = expenseDao.getByCategory(category)

    fun getByRecurring(recurring: Boolean): Flow<List<Expense>> = expenseDao.getByRecurring(recurring)

    fun getBetweenDates(start: Long, end: Long): Flow<List<Expense>> = expenseDao.getBetweenDates(start, end)

    fun getTotalExpenses(): Flow<Double> = expenseDao.getTotalExpenses()

    fun getTotalIncome(): Flow<Double> = expenseDao.getTotalIncome()

    // ------------------ Single Item ------------------

    suspend fun getExpenseById(id: Int): Expense? = expenseDao.getExpenseById(id)
}