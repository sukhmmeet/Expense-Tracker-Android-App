package com.dhaliwal.expensetracker.data.repository

import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.data.local.ExpenseDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class ExpensesRepositoryTest {

    private lateinit var repository: ExpensesRepository
    private lateinit var expenseDao: ExpenseDao

    @Before
    fun setUp() {
        expenseDao = mock(ExpenseDao::class.java)
        repository = ExpensesRepository(expenseDao)
    }

    private fun createExpense(
        id: Int = 1,
        title: String = "Test",
        amount: Double = 100.0,
        type: String = "Expense"
    ) = Expense(
        id = id,
        title = title,
        amount = amount,
        category = "Food",
        date = 1000L,
        note = "",
        type = type,
        isRecurring = false,
        tags = "",
        payment_method = "Cash"
    )

    private fun createExpenseList() = listOf(createExpense())

    @Test
    fun insert_callsDaoInsert() = runTest {
        val expense = createExpense()
        repository.insert(expense)
        verify(expenseDao).insertExpense(expense)
    }

    @Test
    fun update_callsDaoUpdate() = runTest {
        val expense = createExpense()
        repository.update(expense)
        verify(expenseDao).updateExpense(expense)
    }

    @Test
    fun delete_callsDaoDelete() = runTest {
        val expense = createExpense()
        repository.delete(expense)
        verify(expenseDao).deleteExpense(expense)
    }

    @Test
    fun getAllExpenses_returnsDaoData() = runTest {
        val fakeList = createExpenseList()

        `when`(expenseDao.getAllExpenses())
            .thenReturn(flowOf(fakeList))

        val result = repository.getAllExpenses().first()

        assertEquals(fakeList, result)
        verify(expenseDao).getAllExpenses()
    }

    @Test
    fun getOnlyExpenses_returnsDaoData() = runTest {
        val fakeList = createExpenseList()

        `when`(expenseDao.getOnlyExpenses())
            .thenReturn(flowOf(fakeList))

        val result = repository.getOnlyExpenses().first()

        assertEquals(fakeList, result)
        verify(expenseDao).getOnlyExpenses()
    }

    @Test
    fun getOnlyIncomes_returnsDaoData() = runTest {
        val fakeList = listOf(createExpense(type = "Income"))

        `when`(expenseDao.getOnlyIncomes())
            .thenReturn(flowOf(fakeList))

        val result = repository.getOnlyIncomes().first()

        assertEquals(fakeList, result)
        verify(expenseDao).getOnlyIncomes()
    }

    @Test
    fun getByCategory_returnsDaoData() = runTest {
        val fakeList = createExpenseList()

        `when`(expenseDao.getByCategory("Food"))
            .thenReturn(flowOf(fakeList))

        val result = repository.getByCategory("Food").first()

        assertEquals(fakeList, result)
        verify(expenseDao).getByCategory("Food")
    }

    @Test
    fun getByRecurring_returnsDaoData() = runTest {
        val fakeList = createExpenseList()

        `when`(expenseDao.getByRecurring(true))
            .thenReturn(flowOf(fakeList))

        val result = repository.getByRecurring(true).first()

        assertEquals(fakeList, result)
        verify(expenseDao).getByRecurring(true)
    }

    @Test
    fun getBetweenDates_returnsDaoData() = runTest {
        val fakeList = createExpenseList()

        `when`(expenseDao.getBetweenDates(100L, 200L))
            .thenReturn(flowOf(fakeList))

        val result = repository.getBetweenDates(100L, 200L).first()

        assertEquals(fakeList, result)
        verify(expenseDao).getBetweenDates(100L, 200L)
    }

    @Test
    fun getTotalExpenses_returnsDaoValue() = runTest {
        `when`(expenseDao.getTotalExpenses())
            .thenReturn(flowOf(500.0))

        val result = repository.getTotalExpenses().first()

        assertEquals(500.0, result, 0.0)
        verify(expenseDao).getTotalExpenses()
    }

    @Test
    fun getTotalIncome_returnsDaoValue() = runTest {
        `when`(expenseDao.getTotalIncome())
            .thenReturn(flowOf(1000.0))

        val result = repository.getTotalIncome().first()

        assertEquals(1000.0, result, 0.0)
        verify(expenseDao).getTotalIncome()
    }

    @Test
    fun getExpenseById_returnsDaoValue() = runTest {
        val expense = createExpense()

        `when`(expenseDao.getExpenseById(1))
            .thenReturn(expense)

        val result = repository.getExpenseById(1)

        assertEquals(expense, result)
        verify(expenseDao).getExpenseById(1)
    }
}