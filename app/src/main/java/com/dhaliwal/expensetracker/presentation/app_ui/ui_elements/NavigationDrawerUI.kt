package com.dhaliwal.expensetracker.presentation.app_ui.ui_elements

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dhaliwal.expensetracker.data.util.generatePdf
import com.dhaliwal.expensetracker.data.util.sharePdf
import com.dhaliwal.expensetracker.presentation.app_ui.AddExpenseActivity
import com.dhaliwal.expensetracker.presentation.app_ui.SeeExpensesWithFilterActivity
import com.dhaliwal.expensetracker.presentation.view_models.ExpensesViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawerUI(viewModel: ExpensesViewModel, context: Context) {
    var selected by remember { mutableStateOf("Dashboard") }
    val context = context
    val scope = rememberCoroutineScope()
    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
        ) {

            Text(
                text = "Expense Tracker",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(24.dp))

            NavigationDrawerItem(
                label = { Text("Dashboard") },
                selected = selected == "Dashboard",
                onClick = {selected = "Dashboard" }
            )

            NavigationDrawerItem(
                label = { Text("Add Expense") },
                selected = selected == "Add Expense",
                onClick = {
                    selected = "Add Expense"
                    context.startActivity(Intent(context, AddExpenseActivity::class.java ))
                }
            )

            NavigationDrawerItem(
                label = { Text("All Expenses") },
                selected = selected == "All Expenses",
                onClick = {
                    val intent = Intent(context, SeeExpensesWithFilterActivity::class.java)
                    intent.putExtra("TAG", "All")
                    context.startActivity(intent)
                }
            )

            NavigationDrawerItem(
                label = { Text("Export Data") },
                selected = selected == "Export",
                onClick = {
                    selected = "Export"
                    scope.launch {
                        val expenses = viewModel.allExpenses.first()
                        val file = generatePdf(context, expenses)
                        sharePdf(context, file)
                    }
                }
            )

//            NavigationDrawerItem(
//                label = { Text("Settings") },
//                selected = selected == "Settings",
//                onClick = { selected = "Settings" }
//            )
        }
    }
}
