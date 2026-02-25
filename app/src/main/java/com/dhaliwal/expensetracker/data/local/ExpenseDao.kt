package com.dhaliwal.expensetracker.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    // 1️⃣ Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    // 2️⃣ Update
    @Update
    suspend fun updateExpense(expense: Expense)

    // 3️⃣ Delete
    @Delete
    suspend fun deleteExpense(expense: Expense)

    // 4️⃣ Get all
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    // 5️⃣ Get only expenses
    @Query("SELECT * FROM expenses WHERE type = 'Expense' ORDER BY date DESC")
    fun getOnlyExpenses(): Flow<List<Expense>>

    // 6️⃣ Get only incomes
    @Query("SELECT * FROM expenses WHERE type = 'Income' ORDER BY date DESC")
    fun getOnlyIncomes(): Flow<List<Expense>>

    // 7️⃣ Get by category
    @Query("SELECT * FROM expenses WHERE category = :category ORDER BY date DESC")
    fun getByCategory(category: String): Flow<List<Expense>>

    // 8️⃣ Get by recurring
    @Query("SELECT * FROM expenses WHERE isRecurring = :recurring ORDER BY date DESC")
    fun getByRecurring(recurring: Boolean): Flow<List<Expense>>

    // 9️⃣ Get between dates
    @Query("SELECT * FROM expenses WHERE date BETWEEN :start AND :end ORDER BY date DESC")
    fun getBetweenDates(start: Long, end: Long): Flow<List<Expense>>

    // 🔟 Get total expenses
    @Query("SELECT COALESCE(SUM(amount), 0.0) FROM expenses WHERE type = 'Expense'")
    fun getTotalExpenses(): Flow<Double>

    // 1️⃣1️⃣ Get total income
    @Query("SELECT COALESCE(SUM(amount), 0.0) FROM expenses WHERE type = 'Income'")
    fun getTotalIncome(): Flow<Double>

    // 1️⃣2️⃣ Get by ID
    @Query("SELECT * FROM expenses WHERE id = :id LIMIT 1")
    suspend fun getExpenseById(id: Int): Expense?

    // 1️⃣3️⃣ Search by title
    @Query("SELECT * FROM expenses WHERE title LIKE '%' || :query || '%' ORDER BY date DESC")
    fun searchByTitle(query: String): Flow<List<Expense>>

    // 1️⃣4️⃣ Get by payment method
    @Query("SELECT * FROM expenses WHERE payment_method = :method ORDER BY date DESC")
    fun getByPaymentMethod(method: String): Flow<List<Expense>>

    // 1️⃣5️⃣ Get by tags
    @Query("SELECT * FROM expenses WHERE tags LIKE '%' || :tag || '%' ORDER BY date DESC")
    fun getByTag(tag: String): Flow<List<Expense>>
}