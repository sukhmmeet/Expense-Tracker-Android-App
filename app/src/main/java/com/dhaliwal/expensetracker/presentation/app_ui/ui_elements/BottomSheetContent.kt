package com.dhaliwal.expensetracker.presentation.app_ui.ui_elements

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dhaliwal.expensetracker.data.local.Expense

@Composable
fun BottomSheetContent(
    expense: Expense,
    onDelete : () -> Unit,
    onEdit : () -> Unit
){

}

@Preview
@Composable
fun Preview4(){
    BottomSheetContent(
        Expense(
            id = 1,
            title = "Bus ticket",
            amount = 5223.34,
            category = "Transportation",
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