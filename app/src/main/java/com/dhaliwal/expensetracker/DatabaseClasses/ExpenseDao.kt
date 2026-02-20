package com.dhaliwal.expensetracker.DatabaseClasses

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    // 1. Insert a new expense/income
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    // 2. Update an existing expense/income
    @Update
    suspend fun updateExpense(expense: Expense)

    // 3. Delete an expense/income
    @Delete
    suspend fun deleteExpense(expense: Expense)

    // 4. Get all expenses/incomes
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    // 5. Get only expenses
    @Query("SELECT * FROM expenses WHERE type = 'Expense' ORDER BY date DESC")
    fun getOnlyExpenses(): Flow<List<Expense>>

    // 6. Get only incomes
    @Query("SELECT * FROM expenses WHERE type = 'Income' ORDER BY date DESC")
    fun getOnlyIncomes(): Flow<List<Expense>>

    // 7. Get by category
    @Query("SELECT * FROM expenses WHERE category = :category ORDER BY date DESC")
    fun getByCategory(category: String): Flow<List<Expense>>

    // 8. Get by recurring status (instead of frequency)
    @Query("SELECT * FROM expenses WHERE isRecurring = :recurring ORDER BY date DESC")
    fun getByRecurring(recurring: Boolean): Flow<List<Expense>>

    // 9. Get between dates
    @Query("SELECT * FROM expenses WHERE date BETWEEN :start AND :end ORDER BY date DESC")
    fun getBetweenDates(start: Long, end: Long): Flow<List<Expense>>

    // 10. Get total expenses
    @Query("SELECT SUM(amount) FROM expenses WHERE type = 'Expense'")
    fun getTotalExpenses(): Flow<Double>

    // 11. Get total income
    @Query("SELECT SUM(amount) FROM expenses WHERE type = 'Income'")
    fun getTotalIncome(): Flow<Double>

    // 12. Get expense by ID
    @Query("SELECT * FROM expenses WHERE id = :id LIMIT 1")
    suspend fun getExpenseById(id: Int): Expense?
}