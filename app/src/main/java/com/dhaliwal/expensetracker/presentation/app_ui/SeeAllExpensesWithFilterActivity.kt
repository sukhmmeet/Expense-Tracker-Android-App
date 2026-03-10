package com.dhaliwal.expensetracker.presentation.app_ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhaliwal.expensetracker.FloatingButtonMainScreen
import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.data.local.ExpensesConstants
import com.dhaliwal.expensetracker.presentation.app_ui.ui.theme.ExpenseTrackerTheme
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.BottomSheetContent
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.ExpenseItem
import com.dhaliwal.expensetracker.presentation.view_models.ExpensesViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SeeAllExpensesWithFilterActivity : ComponentActivity() {

    val viewModel : ExpensesViewModel by viewModels()

    @SuppressLint("FlowOperatorInvokedInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            
            val intent = intent
            val tag = intent.getStringExtra("TAG").toString()

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
                    SeeAllExpenseUI(
                        tag = tag,
                        expenses = if(tag != "All") viewModel.getByTag(tag) else viewModel.allExpenses,
                        viewModel = viewModel,
                        onFinish = {finish()}
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun SeeAllExpenseUI(
    tag: String = "All",
    expenses: Flow<List<Expense>>,
    viewModel: ExpensesViewModel,
    onFinish : () -> Unit
    ) {
    val expenseList by expenses.collectAsState(initial = emptyList())
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    var selectedExpense by remember { mutableStateOf<Expense?>(null) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "See $tag Expenses",
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
        },
        floatingActionButton = {
            FloatingButtonMainScreen()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 6.dp)
            ) {
                items(items = expenseList) { expense ->
                    ExpenseItem(expense, {
                        selectedExpense = expense
                        showBottomSheet = true
                    })
                }
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier.fillMaxHeight(),
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet = false }
                ) {

                    selectedExpense?.let { exp ->
                        BottomSheetContent(expense = exp, {
                            viewModel.delete(exp)
                            showBottomSheet = false
                        },
                            context = LocalContext.current,
                            hideBottomSheet = {
                                showBottomSheet = false
                            }
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SeeAllExpenseUIPreview() {

    val sampleExpenses = listOf(
        Expense(
            id = 1,
            title = "Lunch",
            amount = 200.0,
            category = ExpensesConstants.category[0], // Food
            date = System.currentTimeMillis(),
            note = "College canteen",
            type = "Expense",
            isRecurring = false,
            tags = ExpensesConstants.tag[1], // Daily
            payment_method = ExpensesConstants.payment_method[0] // Cash
        ),
        Expense(
            id = 2,
            title = "Bus Ticket",
            amount = 50.0,
            category = ExpensesConstants.category[1], // Transport
            date = System.currentTimeMillis(),
            note = "College travel",
            type = "Expense",
            isRecurring = false,
            tags = ExpensesConstants.tag[1], // Daily
            payment_method = ExpensesConstants.payment_method[1] // UPI
        ),
        Expense(
            id = 3,
            title = "Salary",
            amount = 25000.0,
            category = ExpensesConstants.category[9], // Salary
            date = System.currentTimeMillis(),
            note = "Monthly salary",
            type = "Income",
            isRecurring = true,
            tags = ExpensesConstants.tag[3], // Monthly
            payment_method = ExpensesConstants.payment_method[4] // Bank Transfer
        )
    )

//    SeeAllExpenseUI(
//        tag = ExpensesConstants.tag[0], // "All"
//        expenses = flowOf(sampleExpenses),
//    )
}