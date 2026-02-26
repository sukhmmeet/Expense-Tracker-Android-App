package com.dhaliwal.expensetracker

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.data.local.ExpenseDao
import com.dhaliwal.expensetracker.data.local.ExpensesDatabase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class ExpenseDaoTest {

    private lateinit var db: ExpensesDatabase
    private lateinit var dao: ExpenseDao

    private val expense1 = Expense(
        id = 1,
        title = "Game",
        amount = 500.0,
        category = "TopUp",
        date = 20240201,
        note = "",
        type = "Expense",
        isRecurring = true,
        tags = "Monthly",
        payment_method = "Card"
    )

    private val expense2 = Expense(
        title = "Salary",
        amount = 5000.0,
        category = "Work",
        date = 20240202,
        note = "Income",
        type = "Income",
        isRecurring = false,
        tags = "One time",
        payment_method = "Bank"
    )

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ExpensesDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = db.expenseDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    // 1️⃣ Insert Test
    @Test
    fun insertExpense() = runTest {
        dao.insertExpense(expense1)  // Insert first
        val expenses = dao.getAllExpenses().first()
        assertEquals(1, expenses.size)
        assertEquals("Game", expenses[0].title)
    }

    // 2️⃣ Update Test
    @Test
    fun updateExpense() = runTest {
        dao.insertExpense(expense1) // Must insert first!
        val updatedExpense = expense1.copy(1,title = "New Game", amount = 600.0)
        dao.updateExpense(updatedExpense)
        val expenses = dao.getAllExpenses().first()
        assertEquals(1, expenses.size)
        assertEquals("New Game", expenses[0].title)
        assertEquals(600.0, expenses[0].amount)
    }

    // 3️⃣ Delete Test
    @Test
    fun deleteExpense() = runTest {
        dao.insertExpense(expense1) // Must insert first!
        dao.deleteExpense(expense1)
        val expenses = dao.getAllExpenses().first()
        assertEquals(0, expenses.size)
    }

    // 4️⃣ Get All Expenses
    @Test
    fun getAllExpenses() = runTest {
        dao.insertExpense(expense1)
        dao.insertExpense(expense2)
        val expenses = dao.getAllExpenses().first()
        assertEquals(2, expenses.size)
    }

    // 5️⃣ Get Only Expenses
    @Test
    fun getOnlyExpenses() = runTest {
        dao.insertExpense(expense1)
        dao.insertExpense(expense2)
        val expenses = dao.getOnlyExpenses().first()
        assertEquals(1, expenses.size)
        assertEquals("Expense", expenses[0].type)
    }

    // 6️⃣ Get Only Incomes
    @Test
    fun getOnlyIncomes() = runTest {
        dao.insertExpense(expense1)
        dao.insertExpense(expense2)
        val incomes = dao.getOnlyIncomes().first()
        assertEquals(1, incomes.size)
        assertEquals("Income", incomes[0].type)
    }

    // 7️⃣ Get by Category
    @Test
    fun getByCategory() = runTest {
        dao.insertExpense(expense1)
        dao.insertExpense(expense2)
        val topUps = dao.getByCategory("TopUp").first()
        assertEquals(1, topUps.size)
        assertEquals("TopUp", topUps[0].category)
    }

    // 8️⃣ Get by Recurring
    @Test
    fun getByRecurring() = runTest {
        dao.insertExpense(expense1)
        dao.insertExpense(expense2)
        val recurring = dao.getByRecurring(true).first()
        assertEquals(1, recurring.size)
        assertEquals(true, recurring[0].isRecurring)
    }

    // 9️⃣ Get Between Dates
    @Test
    fun getBetweenDates() = runTest {
        dao.insertExpense(expense1)
        dao.insertExpense(expense2)
        val list = dao.getBetweenDates(20240201, 20240202).first()
        assertEquals(2, list.size)
    }

    // 🔟 Get Total Expenses
    @Test
    fun getTotalExpenses() = runTest {
        dao.insertExpense(expense1)
        dao.insertExpense(expense2)
        val total = dao.getTotalExpenses().first()
        assertEquals(500.0, total)
    }

    // 1️⃣1️⃣ Get Total Income
    @Test
    fun getTotalIncome() = runTest {
        dao.insertExpense(expense1)
        dao.insertExpense(expense2)
        val total = dao.getTotalIncome().first()
        assertEquals(5000.0, total)
    }

    // 1️⃣2️⃣ Get by ID
    @Test
    fun getExpenseById() = runTest {
        dao.insertExpense(expense1)
        val expense = dao.getExpenseById(1)
        assertEquals("Game", expense?.title)
        val notFound = dao.getExpenseById(999)
        assertNull(notFound)
    }

    // 1️⃣3️⃣ Search by Title
    @Test
    fun searchByTitle() = runTest {
        dao.insertExpense(expense1)
        dao.insertExpense(expense2)
        val results = dao.searchByTitle("Gam").first()
        assertEquals(1, results.size)
        assertEquals("Game", results[0].title)
    }

    // 1️⃣4️⃣ Get by Payment Method
    @Test
    fun getByPaymentMethod() = runTest {
        dao.insertExpense(expense1)
        dao.insertExpense(expense2)
        val card = dao.getByPaymentMethod("Card").first()
        assertEquals(1, card.size)
        assertEquals("Card", card[0].payment_method)
    }

    // 1️⃣5️⃣ Get by Tag
    @Test
    fun getByTag() = runTest {
        dao.insertExpense(expense1)
        dao.insertExpense(expense2)
        val monthly = dao.getByTag("Monthly").first()
        assertEquals(1, monthly.size)
        assertEquals("Monthly", monthly[0].tags)
    }
}