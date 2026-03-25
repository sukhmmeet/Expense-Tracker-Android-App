package com.dhaliwal.expensetracker.presentation.app_ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhaliwal.expensetracker.FloatingButtonMainScreen
import com.dhaliwal.expensetracker.TopMainScreenBar
import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.data.local.ExpensesConstants
import com.dhaliwal.expensetracker.presentation.app_ui.ui.theme.ExpenseTrackerTheme
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.BottomSheetContent
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.ExpenseItem
import com.dhaliwal.expensetracker.presentation.view_models.ExpensesViewModel
import kotlinx.coroutines.flow.Flow

class SeeExpensesWithFilterActivity : ComponentActivity() {

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
    onFinish: () -> Unit
) {
    var selectedTag by remember { mutableStateOf(tag) }

    val selectedExpensesFlow = remember(selectedTag, viewModel) {
        if (selectedTag == "All") viewModel.allExpenses else viewModel.getByTag(selectedTag)
    }

    val expenseList by selectedExpensesFlow.collectAsState(initial = emptyList())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ExpensesActivityTopBar(
                tag = selectedTag,
                onFinish = onFinish,
                context = LocalContext.current
            )
        },
        floatingActionButton = {
            FloatingButtonMainScreen()
        }
    ) { innerPadding ->
        AllExpenses(
            innerPadding = innerPadding,
            expenses = expenseList,
            viewModel = viewModel,
            selectedTag = selectedTag,
            onClickOnTag = { clickedTag ->
                selectedTag = clickedTag
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllExpenses(
    innerPadding: PaddingValues,
    expenses: List<Expense>,
    viewModel: ExpensesViewModel,
    selectedTag: String,
    onClickOnTag: (tag: String) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var selectedExpense by remember { mutableStateOf<Expense?>(null) }

    Column(
        modifier = Modifier.padding(innerPadding)
    ) {
        if (selectedTag != "search"){
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                items(ExpensesConstants.tag) { tag ->
                    FilterChip(
                        selected = tag == selectedTag,
                        onClick = {
                            onClickOnTag(tag)
                        },
                        label = {
                            Text(
                                text = tag,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.padding(horizontal = 6.dp)
        ) {
            items(
                items = expenses,
                key = { it.id }
            ) { expense ->
                ExpenseItem(
                    expense = expense,
                    onClickItem = {
                        selectedExpense = expense
                        showBottomSheet = true
                    },
                    context = LocalContext.current,
                    viewModel = viewModel
                )
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false }
            ) {
                selectedExpense?.let { exp ->
                    BottomSheetContent(
                        expense = exp,
                        onDelete = {
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

@Composable
fun ExpensesActivityTopBar(
    tag : String = "All",
    onFinish: () -> Unit = {},
    context : Context = LocalContext.current
){
    TopMainScreenBar(
        leftIcon = Icons.Default.ArrowBackIosNew,
        leftIconClick = {
            onFinish()
        },
        rightIconClick = {
            context.startActivity(Intent(context, SearchExpenseActivity::class.java))
        },
        titleText = "Expenses"
    )
}