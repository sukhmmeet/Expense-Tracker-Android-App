package com.dhaliwal.expensetracker.presentation.app_ui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.presentation.app_ui.ui.theme.ExpenseTrackerTheme
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.BottomSheetContent
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.ExpenseItem
import com.dhaliwal.expensetracker.presentation.view_models.ExpensesViewModel

class SearchExpenseActivity : ComponentActivity() {
    val viewModel : ExpensesViewModel by viewModels()
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
                ){
                    SearchExpenseUI(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchExpenseUI(
    viewModel: ExpensesViewModel
) {
    val textFieldState = rememberTextFieldState()
    val expenses by viewModel.allExpenses.collectAsState(initial = emptyList())
    var results by remember { mutableStateOf(emptyList<Expense>()) }
    var query by remember { mutableStateOf("") }

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    var selectedExpense by remember { mutableStateOf<Expense?>(null) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SimpleSearchBar(
            textFieldState = textFieldState,
            onSearch = { q ->
                query = q
                results = searchExpenses(query, expenses)
            }
        )

        AllExpenses(
            innerPadding = PaddingValues(all = 4.dp),
            expenses = if(query != "") results else expenses,
            viewModel = viewModel,
            selectedTag = "search",
            onClickOnTag = {}
        )

//        LazyColumn {
//            items(
//                items = results,
//                key = { it.id }
//            ) { expense ->
//                ExpenseItem(
//                    expense = expense,
//                    viewModel = viewModel,
//                    context = LocalContext.current,
//                    onClickItem = {
//                        selectedExpense = expense
//                        showBottomSheet = true
//                    }
//                )
//            }
//        }
        if(showBottomSheet){
            ModalBottomSheet(
                modifier = Modifier.fillMaxWidth(),
                sheetState = sheetState,
                onDismissRequest = {
                    showBottomSheet = false
                }
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

fun searchExpenses(query: String, expensesList: List<Expense>): List<Expense> {
    if (query.isBlank()) return emptyList()

    return expensesList.filter {
        it.title.contains(query, ignoreCase = true) ||
                it.category.contains(query, ignoreCase = true) ||
                it.tags.contains(query, ignoreCase = true)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSearchBar(
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit
) {
    SearchBar(
        modifier = Modifier
            .padding(all = 6.dp)
            .fillMaxWidth(),
        inputField = {
            SearchBarDefaults.InputField(
                query = textFieldState.text.toString(),
                onQueryChange = {
                    textFieldState.edit {
                        replace(0, length, it)
                    }
                    onSearch(it)
                },
                onSearch = {
                    onSearch(textFieldState.text.toString())
                },
                expanded = false,
                onExpandedChange = {},
                placeholder = { Text("Search expense") }
            )
        },
        expanded = false,
        onExpandedChange = {}
    ) {}
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExpenseTrackerTheme {
        Greeting("Android")

    }
}