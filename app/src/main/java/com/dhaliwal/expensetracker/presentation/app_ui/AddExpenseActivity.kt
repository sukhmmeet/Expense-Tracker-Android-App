package com.dhaliwal.expensetracker.presentation.app_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhaliwal.expensetracker.data.local.ExpensesConstants
import com.dhaliwal.expensetracker.data.local.TransactionType
import com.dhaliwal.expensetracker.presentation.app_ui.ui.theme.ExpenseTrackerTheme

class AddExpenseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            if (isSystemInDarkTheme()) Color.Black
                            else Color.White
                        )
                        .statusBarsPadding()
                ) {
                    AddExpenseUI()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseUI() {

    val focusManager = LocalFocusManager.current

    val titleFocus = remember { FocusRequester() }
    val amountFocus = remember { FocusRequester() }
    val noteFocus = remember { FocusRequester() }

    val modifierTextField = Modifier
        .padding(
            vertical = 2.dp,
            horizontal = 30.dp
        )
        .fillMaxWidth()
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Select Category") }
    var selectedTypeIncomeOrExpense by remember { mutableStateOf(TransactionType.EXPENSE) }
    var selectedPaymentMethod by remember { mutableStateOf("Select Payment Method") }
    var dateInMill: Long? = 0
    var isRecurring by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Add Expense",
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "navigation"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = modifierTextField
                    .focusRequester(titleFocus),
                label = { Text("Title") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { amountFocus.requestFocus() }
                )
            )
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                modifier = modifierTextField
                    .focusRequester(amountFocus),
                label = { Text("Amount") },
                leadingIcon = { Text("₹") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { noteFocus.requestFocus() }
                )
            )
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                modifier = modifierTextField
                    .focusRequester(noteFocus),
                label = { Text("Note") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )

            CustomDropdownField(
                label = "Category",
                options = ExpensesConstants.category,
                selectedValue = selectedCategory,
                onValueSelected = { selectedCategory = it },
                modifier = modifierTextField
            )

            CustomDropdownField(
                label = "Payment Method",
                options = ExpensesConstants.payment_method,
                selectedValue = selectedPaymentMethod,
                onValueSelected = { selectedPaymentMethod = it },
                modifier = modifierTextField
            )

            Row(
                modifier = modifierTextField,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                FilterChip(
                    selected = selectedTypeIncomeOrExpense == TransactionType.EXPENSE,
                    onClick = { selectedTypeIncomeOrExpense = TransactionType.EXPENSE },
                    label = {
                        Text(
                            text = "Expense",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    modifier = Modifier.weight(1F)
                )

                FilterChip(
                    selected = selectedTypeIncomeOrExpense == TransactionType.INCOME,
                    onClick = { selectedTypeIncomeOrExpense = TransactionType.INCOME },
                    label = {
                        Text(
                            text = "Income",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                            },
                    modifier = Modifier.weight(1F)
                )
            }

            DatePickerField(
                modifier = modifierTextField,
            ) { millis ->
                dateInMill = millis
            }

            Row(
                modifier = modifierTextField
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Recurring Transaction",
                    style = MaterialTheme.typography.titleMedium
                )

                Switch(
                    checked = isRecurring,
                    onCheckedChange = { isRecurring = it }
                )
            }
            Button(
                onClick = {

                },
                modifier = Modifier
                    .width(100.dp),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text(
                    text = "Submit"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExpenseTrackerTheme {
        AddExpenseUI()
    }
}