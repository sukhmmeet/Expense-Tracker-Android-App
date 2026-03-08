package com.dhaliwal.expensetracker.presentation.app_ui.ui_elements

import android.content.Context
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
import kotlinx.coroutines.flow.Flow

@Composable
fun RecentTransactionCard(
    modifier: Modifier,
    context: Context = LocalContext.current,
    expenses: Flow<List<Expense>>,
    onClickItem: (Expense) -> Unit
){
    val expenseList by expenses.collectAsState(initial = emptyList())

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 2.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Recent Transactions",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            )

            TextButton(onClick = { }) {
                Text("See All")
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.padding(3.dp).size(10.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier.padding(horizontal = 6.dp)
        ) {
            items(items = expenseList) { expense ->
                ExpenseItem(expense, onClickItem)
            }
        }
    }
}
