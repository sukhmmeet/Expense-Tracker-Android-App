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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhaliwal.expensetracker.presentation.app_ui.AddExpenseActivity
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.BalanceCard
import com.dhaliwal.expensetracker.presentation.app_ui.ui_elements.FinancialOverViewCard
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
    balance : Float = 5000f
) {
    Column(
        modifier = Modifier.padding(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BalanceCard(balance)
        FinancialOverViewCard()
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
                modifier = Modifier.size(50.dp)
            )
        }
        Text(
            "Hello,",
            fontSize = 30.sp,
            modifier = Modifier
                .padding(start = 8.dp),
            fontWeight = FontWeight.Thin,
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