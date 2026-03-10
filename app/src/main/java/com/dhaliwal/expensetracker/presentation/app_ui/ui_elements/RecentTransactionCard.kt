package com.dhaliwal.expensetracker.presentation.app_ui.ui_elements

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.presentation.app_ui.SeeAllExpensesWithFilterActivity
import kotlinx.coroutines.flow.Flow

@Composable
fun RecentTransactionCard(
    modifier: Modifier,
    context: Context = LocalContext.current,
    expenses: List<Expense>,
    onClickItem: (Expense) -> Unit,
    tag : String
){
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Transactions",
                style = MaterialTheme.typography.titleMedium
            )

            TextButton(onClick = {
                val intent = Intent(context, SeeAllExpensesWithFilterActivity::class.java)
                intent.putExtra("TAG", tag)
                context.startActivity(intent)
            }) {
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

        LazyColumn(
            modifier = Modifier.padding(horizontal = 6.dp)
        ) {
            items(items = expenses) { expense ->
                ExpenseItem(expense, onClickItem)
            }
        }
    }
}
