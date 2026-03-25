package com.dhaliwal.expensetracker.presentation.app_ui.ui_elements

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.presentation.app_ui.SeeExpensesWithFilterActivity
import com.dhaliwal.expensetracker.presentation.view_models.ExpensesViewModel

@Composable
fun RecentTransaction(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    expenses: List<Expense>,
    onClickItem: (Expense) -> Unit,
    tag: String,
    viewModel: ExpensesViewModel
) {
    androidx.compose.foundation.layout.Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Transactions",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

            TextButton(
                onClick = {
                    val intent = Intent(context, SeeExpensesWithFilterActivity::class.java)
                    intent.putExtra("TAG", tag)
                    context.startActivity(intent)
                }
            ) {
                Text("See All")
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(16.dp)
                )
            }
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
        ) {
            expenses.take(6).forEach { expense ->
                key(expense.id) {
                    ExpenseItem(
                        expense = expense,
                        onClickItem = onClickItem,
                        viewModel = viewModel,
                        context = context
                    )
                }
            }
        }

//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 6.dp),
//            userScrollEnabled = false
//        ) {
//            items(
//                items = expenses.take(6),
//                key = { it.id }
//            ) { expense ->
//                ExpenseItem(
//                    expense = expense,
//                    onClickItem = onClickItem,
//                    viewModel = viewModel,
//                    context = context
//                )
//            }
//        }
    }
}
