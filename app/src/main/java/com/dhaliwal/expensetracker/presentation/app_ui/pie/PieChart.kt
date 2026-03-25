package com.dhaliwal.expensetracker.presentation.app_ui.pie

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import com.dhaliwal.expensetracker.presentation.app_ui.pie.renderer.FilledSliceDrawer
import com.dhaliwal.expensetracker.presentation.app_ui.pie.renderer.SliceDrawer

@Composable
fun PieChart(
    pies: Pies,
    modifier: Modifier = Modifier,
    animation: AnimationSpec<Float> = tween(durationMillis = 1000),
    sliceDrawer: SliceDrawer = FilledSliceDrawer()
) {
    val transitionProgress = remember(pies.slices) { Animatable(initialValue = 0f) }

    LaunchedEffect(pies.slices) {
        transitionProgress.snapTo(0f)
        transitionProgress.animateTo(1f, animationSpec = animation)
    }

    DrawChart(
        pies = pies,
        modifier = modifier.fillMaxSize(),
        progress = transitionProgress.value,
        sliceDrawer = sliceDrawer
    )
}

@Composable
private fun DrawChart(
    pies: Pies,
    modifier: Modifier,
    progress: Float,
    sliceDrawer: SliceDrawer
) {
    val slices = pies.slices

    Canvas(modifier = modifier) {
        drawIntoCanvas {
            var startArc = 0f

            slices.forEach { slice ->
                val arc = angleOf(
                    value = slice.value,
                    totalValue = pies.totalSize(),
                    progress = progress
                )

                sliceDrawer.drawSlice(
                    drawScope = this,
                    canvas = drawContext.canvas,
                    area = size,
                    startAngle = startArc,
                    sweepAngle = arc,
                    slice = slice
                )

                startArc += arc
            }
        }
    }
}

fun angleOf(value: Float, totalValue: Float, progress: Float): Float {
    if (totalValue == 0f) return 0f
    return 360f * (value * progress) / totalValue
}