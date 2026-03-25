package com.dhaliwal.expensetracker.presentation.app_ui.ui_elements

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.data.util.Util
import com.dhaliwal.expensetracker.presentation.app_ui.pie.PieChart
import com.dhaliwal.expensetracker.presentation.app_ui.pie.Pies
import com.dhaliwal.expensetracker.presentation.app_ui.pie.Slice
import com.dhaliwal.expensetracker.presentation.app_ui.pie.renderer.FilledSliceDrawer
import com.dhaliwal.expensetracker.presentation.app_ui.ui.theme.ExpenseTrackerTheme

@Composable
fun PieChartComposable(
    expenses: List<Expense>,
    modifier: Modifier = Modifier
) {
    val expenseSlices = expenses
        .filter { it.type == "Expense" }
        .groupBy { it.category }
        .map { (category, expenseList) ->
            Slice(
                value = expenseList.sumOf { it.amount }.toFloat(),
                color = Util().getCategoryColor(category)
            )
        }
        .filter { it.value > 0f }

    PieChart(
        pies = Pies(expenseSlices),
        modifier = modifier,
        animation = tween(4000),
        sliceDrawer = FilledSliceDrawer(thickness = 60f)
    )
}

@Composable
fun PieChartWithLegend(
    expenses: List<Expense>,
    modifier: Modifier = Modifier
) {
    val legendItems = getLegendItems(expenses)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PieChartComposable(
            expenses = expenses,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        PieChartLegend(
            items = legendItems,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PieChartLegend(
    items: List<PieLegendItem>,
    modifier: Modifier = Modifier
) {
    val totalAmount = items.sumOf { it.amount.toDouble() }.toFloat()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            PieChartLegendItem(
                item = item,
                totalAmount = totalAmount
            )
        }
    }
}

@Composable
fun PieChartLegendItem(
    item: PieLegendItem,
    totalAmount: Float,
    modifier: Modifier = Modifier
) {
    val percentage = if (totalAmount == 0f) 0 else ((item.amount / totalAmount) * 100).toInt()

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(item.color, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = item.category,
            modifier = Modifier.weight(1f),
            maxLines = 1
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "₹${item.amount.toInt()} ($percentage%)",
            maxLines = 1
        )
    }
}

fun getLegendItems(expenses: List<Expense>): List<PieLegendItem> {
    return expenses
        .filter { it.type == "Expense" }
        .groupBy { it.category }
        .map { (category, expenseList) ->
            PieLegendItem(
                category = category,
                amount = expenseList.sumOf { it.amount }.toFloat(),
                color = Util().getCategoryColor(category)
            )
        }
        .sortedByDescending { it.amount }
}


data class PieLegendItem(
    val category: String,
    val amount: Float,
    val color: Color
)

@Preview(showBackground = true)
@Composable
fun Preview52() {
    val sampleExpenses = listOf(
        Expense(
            id = 1,
            title = "Pizza",
            amount = 500.0,
            category = "Food",
            date = System.currentTimeMillis(),
            note = "",
            type = "Expense",
            isRecurring = false,
            tags = "dinner",
            payment_method = "Cash"
        ),
        Expense(
            id = 2,
            title = "Bus Ticket",
            amount = 200.0,
            category = "Transport",
            date = System.currentTimeMillis(),
            note = "",
            type = "Expense",
            isRecurring = false,
            tags = "travel",
            payment_method = "UPI"
        ),
        Expense(
            id = 3,
            title = "Movie",
            amount = 350.0,
            category = "Entertainment",
            date = System.currentTimeMillis(),
            note = "",
            type = "Expense",
            isRecurring = false,
            tags = "fun",
            payment_method = "Card"
        ),
        Expense(
            id = 4,
            title = "Medicine",
            amount = 150.0,
            category = "Health",
            date = System.currentTimeMillis(),
            note = "",
            type = "Expense",
            isRecurring = false,
            tags = "health",
            payment_method = "Cash"
        ),
        Expense(
            id = 5,
            title = "Shopping",
            amount = 700.0,
            category = "Shopping",
            date = System.currentTimeMillis(),
            note = "",
            type = "Expense",
            isRecurring = false,
            tags = "clothes",
            payment_method = "UPI"
        )
    )

    ExpenseTrackerTheme {
        Surface {
            PieChartWithLegend(expenses = sampleExpenses)
        }
    }
}