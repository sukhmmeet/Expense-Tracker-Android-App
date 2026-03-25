package com.dhaliwal.expensetracker

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhaliwal.expensetracker.data.util.Util
import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.data.local.ExpensesConstants
import com.dhaliwal.expensetracker.data.local.TotalAmount
import com.dhaliwal.expensetracker.presentation.app_ui.AddExpenseActivity
import com.dhaliwal.expensetracker.presentation.app_ui.SearchExpenseActivity
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.BalanceCard
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.BottomSheetContent
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.NavigationDrawerUI
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.PieChartWithLegend
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.RecentTransaction
import com.dhaliwal.expensetracker.presentation.view_models.ExpensesViewModel
import com.dhaliwal.expensetracker.ui.theme.ExpenseTrackerTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.getValue

class MainActivity : ComponentActivity() {

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
                    MainScreen(
                        viewModel = viewModel,
                        context = LocalContext.current
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: ExpensesViewModel, context: Context) {
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerUI(viewModel, context = LocalContext.current)
        }
    ) {

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopMainScreenBar(
                    leftIconClick = {
                        scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                        }
                    },
                    rightIconClick = {
                        context.startActivity(Intent(context, SearchExpenseActivity::class.java))
                    }
                )
            },
            floatingActionButton = {
                FloatingButtonMainScreen()
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                MainActivityContent(
                    viewModel = viewModel
                )
            }
        }
    }
}

@SuppressLint("FlowOperatorInvokedInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityContent(viewModel: ExpensesViewModel) {
    var filter by remember { mutableStateOf("All") }
    val allExpenses by viewModel.allExpenses.collectAsState(initial = emptyList())
    var expenses : Flow<List<Expense>>
    expenses = if(filter == "All"){
        viewModel.allExpenses
    }else {
        viewModel.getByTag(filter)
    }
    val totalExpenseAndIncome by Util()
        .getTotalExpenseAndIncome(expenses)
        .collectAsState(initial = TotalAmount(0.0, 0.0))
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    var expense by remember { mutableStateOf<Expense?>(null) }
    val modifier1 = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 6.dp)
    Column(
        modifier = Modifier
            .padding(3.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BalanceCard(totalExpenseAndIncome.totalIncome - totalExpenseAndIncome.totalExpense, modifier1)

        Card(
            modifier = modifier1,
            shape = RoundedCornerShape(24.dp)
        ){
            PieChartWithLegend(allExpenses)
        }
        LazyRow(
            modifier = modifier1
        ) {
            items(ExpensesConstants.tag){ tag ->
                FilterChip(
                    selected = tag == filter,
                    onClick = { filter = tag },
                    label = {
                        Text(
                            text = tag,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    modifier = Modifier.weight(1F).padding(horizontal = 4.dp)
                )
            }
        }
        val expenseList by expenses
            .map { it.take(6) }
            .collectAsState(initial = emptyList())

        RecentTransaction(
            modifier = modifier1,
            expenses = expenseList,
            onClickItem = { clickedExpense ->
                expense = clickedExpense
                showBottomSheet = true
            },
            tag = filter,
            viewModel = viewModel
        )
        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false }
            ) {

                expense?.let { exp ->
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



@Composable
fun FloatingButtonMainScreen() {
    val context = LocalContext.current
    FloatingActionButton(
        onClick = {
            context.startActivity(
                Intent(context, AddExpenseActivity::class.java)
            )
        },
        shape = CircleShape,
        containerColor = Color(0xFF4CAF50),
        modifier = Modifier.size(56.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            tint = Color.White
        )
    }
}

@Composable
fun TopMainScreenBar(
    leftIcon : ImageVector = Icons.Default.Menu,
    leftIconClick: () -> Unit = {},
    rightIcon : ImageVector = Icons.Default.Search,
    rightIconClick : () -> Unit = {},
    titleText : String = "Hello,"
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                leftIconClick()
            }
        ) {
            Icon(
                imageVector = leftIcon,
                contentDescription = "menu",
                modifier = Modifier.size(30.dp)
            )
        }
        Text(
            titleText,
            fontSize = 30.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, end = 8.dp),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )
        IconButton(
            onClick = {
                rightIconClick()
            }
        ) {
            Icon(
                imageVector = rightIcon,
                contentDescription = "menu",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}
