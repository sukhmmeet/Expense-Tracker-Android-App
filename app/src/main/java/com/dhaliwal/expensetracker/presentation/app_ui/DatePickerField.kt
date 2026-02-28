package com.dhaliwal.expensetracker.presentation.app_ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    modifier: Modifier = Modifier,
    label: String = "Select Date",
    onDateSelected: (Long?) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    val datePickerState = rememberDatePickerState()

    val formattedDate = selectedDate?.let {
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        formatter.format(Date(it))
    } ?: "Select Date"

    Button(
        onClick = { showDialog = true },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(vertical = 14.dp)
    ) {
        Text(
            text = if (formattedDate != "Select Date")
                "Selected Date: $formattedDate"
            else
                "Select Date",
            fontSize = 16.sp
        )
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedDate = datePickerState.selectedDateMillis
                        onDateSelected(datePickerState.selectedDateMillis)
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}