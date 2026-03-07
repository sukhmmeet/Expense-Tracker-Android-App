package com.dhaliwal.expensetracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.dhaliwal.expensetracker.data.local.Expense
import com.dhaliwal.expensetracker.data.local.ExpensesConstants
import com.dhaliwal.expensetracker.data.local.TransactionType
import com.dhaliwal.expensetracker.presentation.app_ui.AddExpenseActivity
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.BalanceCard
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.FinancialOverViewCard
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.RecentTransactionCard
import com.dhaliwal.expensetracker.presentation.view_models.ExpensesViewModel
import com.dhaliwal.expensetracker.ui.theme.ExpenseTrackerTheme
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
//                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
@Composable
fun MainScreen(
//    viewModel: ExpensesViewModel = viewModel()
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopMainScreenBar()
        },
        floatingActionButton = {
            FloatingButtonMainScreen()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            MainActivityContent()
        }
    }
}

@Composable
fun MainActivityContent(
    balance : Float = 5000f,
) {
    val modifier1 = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 6.dp)
    var filter by remember { mutableStateOf("All") }
    Column(
        modifier = Modifier.padding(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BalanceCard(balance, modifier1)
        FinancialOverViewCard(
            modifier = modifier1
        )
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
        val expenses = listOf(
            Expense(
                title = "Lunch",
                amount = 250.0,
                category = "Food",
                date = System.currentTimeMillis(),
                note = "College canteen",
                type = "Expense",
                isRecurring = false,
                tags = "Daily",
                payment_method = "Cash"
            ),
            Expense(
                title = "Bus Ticket",
                amount = 60.0,
                category = "Transport",
                date = System.currentTimeMillis(),
                note = "Bus to college",
                type = "Expense",
                isRecurring = false,
                tags = "Daily",
                payment_method = "Cash"
            ),
            Expense(
                title = "Electricity Bill",
                amount = 1800.0,
                category = "Bills",
                date = System.currentTimeMillis(),
                note = "Monthly bill",
                type = "Expense",
                isRecurring = true,
                tags = "Monthly",
                payment_method = "Net Banking"
            ),
            Expense(
                title = "House Rent",
                amount = 12000.0,
                category = "Rent",
                date = System.currentTimeMillis(),
                note = "March rent",
                type = "Expense",
                isRecurring = true,
                tags = "Monthly",
                payment_method = "Bank Transfer"
            ),
            Expense(
                title = "Movie",
                amount = 400.0,
                category = "Entertainment",
                date = System.currentTimeMillis(),
                note = "Cinema with friends",
                type = "Expense",
                isRecurring = false,
                tags = "Occasional",
                payment_method = "UPI"
            ),
            Expense(
                title = "Doctor Visit",
                amount = 700.0,
                category = "Health",
                date = System.currentTimeMillis(),
                note = "General checkup",
                type = "Expense",
                isRecurring = false,
                tags = "One Time",
                payment_method = "Cash"
            ),
            Expense(
                title = "New Shoes",
                amount = 2200.0,
                category = "Shopping",
                date = System.currentTimeMillis(),
                note = "Sports shoes",
                type = "Expense",
                isRecurring = false,
                tags = "Occasional",
                payment_method = "Credit Card"
            ),
            Expense(
                title = "Programming Book",
                amount = 950.0,
                category = "Education",
                date = System.currentTimeMillis(),
                note = "DSA practice book",
                type = "Expense",
                isRecurring = false,
                tags = "One Time",
                payment_method = "UPI"
            ),
            Expense(
                title = "Mutual Fund SIP",
                amount = 3000.0,
                category = "Investment",
                date = System.currentTimeMillis(),
                note = "Monthly investment",
                type = "Expense",
                isRecurring = true,
                tags = "Monthly",
                payment_method = "Bank Transfer"
            ),
            Expense(
                title = "Salary Credit",
                amount = 45000.0,
                category = "Salary",
                date = System.currentTimeMillis(),
                note = "Company salary",
                type = "Income",
                isRecurring = true,
                tags = "Monthly",
                payment_method = "Bank Transfer"
            ),
            Expense(
                title = "Misc Expense",
                amount = 300.0,
                category = "Others",
                date = System.currentTimeMillis(),
                note = "Random expense",
                type = "Expense",
                isRecurring = false,
                tags = "Occasional",
                payment_method = "UPI"
            )
        )
        RecentTransactionCard(
            modifier = modifier1,
            context = LocalContext.current,
            expenses
        )
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

) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {

            }
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "menu",
                modifier = Modifier.size(30.dp)
            )
        }
        Text(
            "Hello,",
            fontSize = 30.sp,
            modifier = Modifier
                .padding(start = 8.dp),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExpenseTrackerTheme {
        MainScreen()
    }
}