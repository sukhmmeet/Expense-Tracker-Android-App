package com.dhaliwal.expensetracker.presentation.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dhaliwal.expensetracker.data.db.DatabaseProvider
import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.data.repository.ExpensesRepository
import kotlinx.coroutines.launch


class ExpensesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ExpensesRepository(DatabaseProvider.getDatabase(application).getDao())

    val allExpenses = repository.getAllExpenses()

    fun insert(expense: Expense) = viewModelScope.launch { repository.insert(expense) }
    fun update(expense: Expense) = viewModelScope.launch { repository.update(expense) }
    fun delete(expense: Expense) = viewModelScope.launch { repository.delete(expense) }

    fun getByCategory(category: String) = repository.getByCategory(category)
}