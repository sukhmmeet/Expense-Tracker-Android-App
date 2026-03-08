package com.dhaliwal.expensetracker.presentation.app_ui.ui_elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.dhaliwal.expensetracker.data.Util.Util
import com.dhaliwal.expensetracker.data.local.Expense

@Composable
fun ExpenseItem(
    expense: Expense,
    onClickItem: (Expense) -> Unit = {},

    ){
    Row(
        Modifier.fillMaxWidth().padding(3.dp).clickable(enabled = true, onClick = {
            onClickItem(expense)
        }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Util().getLogo(expense.category)),
            contentDescription = "Expense Type",
            tint = Color.Unspecified,
            modifier = Modifier.padding(8.dp).size(50.dp)
        )
        Column() {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp),
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
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(expense.tags)
                Text("₹${Util().convertMillisToDate(expense.date)}")
            }
        }
    }
}

@Preview
@Composable
fun Preview3(){
    val expenses = listOf(
        Expense(
            title = "Lunch",
            amount = 250.0,
            category = "Food",
            date = System.currentTimeMillis(),
            note = "College canteen",
            type = "Expense",
            isRecurring = false,
            tags = "Daily",
            payment_method = "Cash"
        ),
        Expense(
            title = "Bus Ticket",
            amount = 60.0,
            category = "Transport",
            date = System.currentTimeMillis(),
            note = "Bus to college",
            type = "Expense",
            isRecurring = false,
            tags = "Daily",
            payment_method = "Cash"
        ),
        Expense(
            title = "Electricity Bill",
            amount = 1800.0,
            category = "Bills",
            date = System.currentTimeMillis(),
            note = "Monthly bill",
            type = "Expense",
            isRecurring = true,
            tags = "Monthly",
            payment_method = "Net Banking"
        ),
        Expense(
            title = "House Rent",
            amount = 12000.0,
            category = "Rent",
            date = System.currentTimeMillis(),
            note = "March rent",
            type = "Expense",
            isRecurring = true,
            tags = "Monthly",
            payment_method = "Bank Transfer"
        ),
        Expense(
            title = "Movie",
            amount = 400.0,
            category = "Entertainment",
            date = System.currentTimeMillis(),
            note = "Cinema with friends",
            type = "Expense",
            isRecurring = false,
            tags = "Occasional",
            payment_method = "UPI"
        ),
        Expense(
            title = "Doctor Visit",
            amount = 700.0,
            category = "Health",
            date = System.currentTimeMillis(),
            note = "General checkup",
            type = "Expense",
            isRecurring = false,
            tags = "One Time",
            payment_method = "Cash"
        ),
        Expense(
            title = "New Shoes",
            amount = 2200.0,
            category = "Shopping",
            date = System.currentTimeMillis(),
            note = "Sports shoes",
            type = "Expense",
            isRecurring = false,
            tags = "Occasional",
            payment_method = "Credit Card"
        ),
        Expense(
            title = "Programming Book",
            amount = 950.0,
            category = "Education",
            date = System.currentTimeMillis(),
            note = "DSA practice book",
            type = "Expense",
            isRecurring = false,
            tags = "One Time",
            payment_method = "UPI"
        ),
        Expense(
            title = "Mutual Fund SIP",
            amount = 3000.0,
            category = "Investment",
            date = System.currentTimeMillis(),
            note = "Monthly investment",
            type = "Expense",
            isRecurring = true,
            tags = "Monthly",
            payment_method = "Bank Transfer"
        ),
        Expense(
            title = "Salary Credit",
            amount = 45000.0,
            category = "Salary",
            date = System.currentTimeMillis(),
            note = "Company salary",
            type = "Income",
            isRecurring = true,
            tags = "Monthly",
            payment_method = "Bank Transfer"
        ),
        Expense(
            title = "Misc Expense",
            amount = 300.0,
            category = "Others",
            date = System.currentTimeMillis(),
            note = "Random expense",
            type = "Expense",
            isRecurring = false,
            tags = "Occasional",
            payment_method = "UPI"
        )
    )
    ExpenseItem(expenses[0], {})
}