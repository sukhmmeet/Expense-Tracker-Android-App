package com.dhaliwal.expensetracker.presentation.app_ui.ui_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.dhaliwal.expensetracker.data.Util.Util
import com.dhaliwal.expensetracker.data.local.Expense

@Composable
fun BottomSheetContent(
    expense: Expense,
    onDelete : () -> Unit,
    onEdit : () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth().padding(13.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(Util().getLogo(expense.category)),
            contentDescription = "category icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(50.dp).padding(3.dp)
        )
        BottomSheetRow(
            textFirst = expense.title,
            textSecond = if (expense.type=="Income") "₹${expense.amount}" else "₹${expense.amount * -1}",
            fontSize = MaterialTheme.typography.titleMedium.fontSize
        )
        HorizontalDivider(
            thickness = 2.dp
        )

        BottomSheetRow(
            textFirst = "Category:",
            textSecond = expense.category
        )
        BottomSheetRow(
            textFirst = "Payment:",
            textSecond = expense.payment_method
        )
        BottomSheetRow(
            textFirst = "Type:",
            textSecond = expense.type
        )
        BottomSheetRow(
            textFirst = "Date:",
            textSecond = Util().convertMillisToDate(expense.date)
        )
        BottomSheetRow(
            textFirst = "Recurring:",
            textSecond = expense.tags
        )
        BottomSheetRow(
            textFirst = "Note:",
            textSecond = expense.note
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 4.dp,
                    bottom = 4.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonBottomSheet(
                text = "Edit",
                modifier = Modifier.weight(1f),
                color = Color(0xFF4CAF50)
            ) { onEdit() }

            ButtonBottomSheet(
                text = "Delete",
                modifier = Modifier.weight(1f),
                color = Color(0xFF4CAF50)
            ) { onDelete() }
        }
    }
}

@Composable
fun ButtonBottomSheet(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    ElevatedButton(
        modifier = modifier.padding(5.dp),
        onClick = onClick,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = color
        )
    ) {
        Text(text, color = Color.DarkGray)
    }
}

@Composable
fun BottomSheetRow(
    textFirst : String,
    textSecond : String,
    fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(
            start = 8.dp,
            end = 8.dp,
            top = 4.dp,
            bottom = 4.dp
        ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = textFirst,
            fontSize = fontSize
        )
        Text(
            text = textSecond,
            fontSize = fontSize
        )
    }
}

@Preview
@Composable
fun Preview4(){
    Surface() {
        BottomSheetContent(
            Expense(
                id = 1,
                title = "Bus ticket",
                amount = 5223.34,
                category = "Transport",
                date = System.currentTimeMillis(),
                note = "College to Home",
                type = "Expense",
                isRecurring = true,
                tags = "Daily",
                payment_method = "Cash"
            ),
            onDelete = {},
            onEdit = {}
        )
    }
}