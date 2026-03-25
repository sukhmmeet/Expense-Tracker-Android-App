package com.dhaliwal.expensetracker.presentation.app_ui.ui_elements

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dhaliwal.expensetracker.data.util.Util
import com.dhaliwal.expensetracker.data.util.openEditActivity
import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.presentation.view_models.ExpensesViewModel

@Composable
fun ExpenseItem(
    expense: Expense,
    onClickItem: (Expense) -> Unit = {},
    viewModel: ExpensesViewModel,
    context: Context
    ){
    var isEditTriggered by remember { mutableStateOf(false) }

    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    if (!isEditTriggered) {
                        isEditTriggered = true
                        openEditActivity(context, expense)
                    }
                    false
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    viewModel.delete(expense)
                    true
                }
                else -> false
            }
        }
    )
    LaunchedEffect(swipeToDismissBoxState.currentValue) {
        if (swipeToDismissBoxState.currentValue == SwipeToDismissBoxValue.Settled) {
            isEditTriggered = false
        }
    }
    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        modifier = Modifier.fillMaxWidth(),
        backgroundContent = {
            when (swipeToDismissBoxState.dismissDirection) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Swipe edit",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Blue)
                            .wrapContentSize(Alignment.CenterStart)
                            .padding(12.dp),
                        tint = Color.White
                    )
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Swipe remove item",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                            .wrapContentSize(Alignment.CenterEnd)
                            .padding(12.dp),
                        tint = Color.White
                    )
                }
                SwipeToDismissBoxValue.Settled -> {}
            }
        }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(3.dp)
                .clickable(enabled = true, onClick = {
                    onClickItem(expense)
                }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Util().getLogo(expense.category)),
                contentDescription = "Expense Type",
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(8.dp)
                    .size(50.dp)
            )
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = expense.title,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        modifier = Modifier,
                        text = "₹${expense.amount}",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }
                Text(
                    expense.category,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(expense.tags)
                    Text("₹${Util().convertMillisToDate(expense.date)}")
                }
            }
        }
    }
}
