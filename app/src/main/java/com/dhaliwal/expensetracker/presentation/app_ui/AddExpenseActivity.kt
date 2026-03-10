package com.dhaliwal.expensetracker.presentation.app_ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.data.local.ExpensesConstants
import com.dhaliwal.expensetracker.data.local.TransactionType
import com.dhaliwal.expensetracker.presentation.app_ui.ui.theme.ExpenseTrackerTheme
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.CustomDropdownField
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.DatePickerField
import com.dhaliwal.expensetracker.presentation.view_models.ExpensesViewModel
import java.util.Locale
import java.util.Locale.getDefault

class AddExpenseActivity : ComponentActivity() {

    private val viewModel: ExpensesViewModel by viewModels()

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

                    val intent = intent
                    val id = intent.getIntExtra("id", -1)
                    val title = intent.getStringExtra("title") ?: ""
                    val amount = intent.getDoubleExtra("amount", 0.0)
                    val category = intent.getStringExtra("category") ?: ""
                    val date = intent.getLongExtra("date", 0L)
                    val note = intent.getStringExtra("note") ?: ""
                    val type = intent.getStringExtra("type") ?: ""
                    val isRecurring = intent.getBooleanExtra("isRecurring", false)
                    val tags = intent.getStringExtra("tags") ?: ""
                    val paymentMethod = intent.getStringExtra("payment_method") ?: ""

                    if(id > -1){
                        AddExpenseUI(
                            viewModel = viewModel,
                            context = LocalContext.current,
                            onFinish = {this.finish()},
                            expense = Expense(
                                id = id,
                                title = title,
                                amount = amount,
                                category = category,
                                date = date,
                                note = note,
                                type = type,
                                isRecurring = isRecurring,
                                tags = tags,
                                payment_method = paymentMethod
                            ),
                            appBarText = "Update Expense"
                        )
                    }else {
                        AddExpenseUI(
                            viewModel = viewModel,
                            context = LocalContext.current,
                            onFinish = {this.finish()},
                        )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseUI(
    appBarText : String = "Add Expense",
    viewModel: ExpensesViewModel = viewModel(),
    context: Context = LocalContext.current,
    onFinish: () -> Unit,
    expense: Expense = Expense(
        title = "",
        amount = 0.0,
        category = "Select Category",
        date = 0L,
        note = "",
        type = "Expense",
        isRecurring = false,
        tags = "Select Tag",
        payment_method = "Select Payment Method"
    )
) {

    val focusManager = LocalFocusManager.current

    val titleFocus = remember { FocusRequester() }
    val amountFocus = remember { FocusRequester() }
    val noteFocus = remember { FocusRequester() }

    val modifierForAllElements = Modifier
        .padding(
            vertical = 2.dp,
            horizontal = 30.dp
        )
        .fillMaxWidth()
    var selectedExpense by remember(expense.id) {
        mutableStateOf(
            if (expense.type.isBlank()) expense.copy(type = "Expense")
            else expense
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = appBarText,
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onFinish()
                    }) {
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
                value = selectedExpense.title,
                onValueChange = { selectedExpense = selectedExpense.copy(title = it) },
                modifier = modifierForAllElements
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
                value = if (selectedExpense.amount == 0.0) "" else selectedExpense.amount.toString(),
                onValueChange = {
                    selectedExpense = selectedExpense.copy(
                        amount = it.toDoubleOrNull() ?: 0.0
                    )
                },
                modifier = modifierForAllElements
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
                value = selectedExpense.note,
                onValueChange = { selectedExpense = selectedExpense.copy(note = it) },
                modifier = modifierForAllElements
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
                selectedValue = selectedExpense.category,
                onValueSelected = {
                    selectedExpense = selectedExpense.copy(category = it)
                },
                modifier = modifierForAllElements
            )

            CustomDropdownField(
                label = "Payment Method",
                options = ExpensesConstants.payment_method,
                selectedValue = selectedExpense.payment_method,
                onValueSelected = {
                    selectedExpense = selectedExpense.copy(payment_method = it)
                },
                modifier = modifierForAllElements
            )
            CustomDropdownField(
                label = "Payment Tag",
                options = ExpensesConstants.tag,
                selectedValue = selectedExpense.tags,
                onValueSelected = {
                    selectedExpense = selectedExpense.copy(tags = it)
                },
                modifier = modifierForAllElements
            )


            val type = selectedExpense.type
            val bool = selectedExpense.type == TransactionType.EXPENSE.value

            Row(
                modifier = modifierForAllElements,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                FilterChip(
                    selected = selectedExpense.type == TransactionType.EXPENSE.value,
                    onClick = {
                        selectedExpense = selectedExpense.copy(type = TransactionType.EXPENSE.value)
                    },
                    label = {
                        Text(
                            text = "Expense",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    modifier = Modifier.weight(1f)
                )

                FilterChip(
                    selected = selectedExpense.type == TransactionType.INCOME.value,
                    onClick = {
                        selectedExpense = selectedExpense.copy(type = TransactionType.INCOME.value)
                    },
                    label = {
                        Text(
                            text = "Income",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            DatePickerField(
                modifier = modifierForAllElements,
                initialDate = selectedExpense.date
            ) { millis ->
                selectedExpense = selectedExpense.copy(date = millis?: 0L)
            }

            Row(
                modifier = modifierForAllElements
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
                    checked = selectedExpense.isRecurring,
                    onCheckedChange = {
                        selectedExpense = selectedExpense.copy(isRecurring = it)
                    }
                )
            }

            Button(
                onClick = {
                    if(
                        selectedExpense.title.isNotBlank() &&
                        selectedExpense.category != "Select Category" &&
                        selectedExpense.payment_method != "Select Payment Method" &&
                        selectedExpense.date != 0L &&
                        selectedExpense.tags != "Select Tag"
                    ){
                        viewModel.insert(selectedExpense)
                        onFinish()
                    } else {
                        Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.width(100.dp),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text("Submit")
            }
        }
    }
}
