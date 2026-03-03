package com.dhaliwal.expensetracker.presentation.app_ui.ui_elements

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExpenseDonutChart(
    income: Float,
    expense: Float,
    modifier: Modifier = Modifier
) {
    val total = income + expense
    val gapAngle = 4f

    val incomeAngle = if (total == 0f) 0f else (income / total) * 360f
    val expenseAngle = 360f - incomeAngle

    val animatedIncomeAngle by animateFloatAsState(
        targetValue = incomeAngle,
        animationSpec = tween(1000),
        label = ""
    )

    Box(
        modifier = modifier.size(220.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            val strokeWidth = size.minDimension * 0.12f
            val radiusOffset = strokeWidth / 2

            val arcSize = Size(
                width = size.width - strokeWidth,
                height = size.height - strokeWidth
            )

            val topLeft = Offset(radiusOffset, radiusOffset)

            // Income
            if (total > 0f) {
                drawArc(
                    color = Color(0xFF4CAF50),
                    startAngle = -90f + gapAngle / 2,
                    sweepAngle = animatedIncomeAngle - gapAngle,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round
                    )
                )

                // Expense
                drawArc(
                    color = Color(0xFFF44336),
                    startAngle = -90f + animatedIncomeAngle + gapAngle / 2,
                    sweepAngle = expenseAngle - gapAngle,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round
                    )
                )
            }
        }

        // Center Content
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Balance",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Text(
                text = "₹ ${(income - expense).toInt()}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}