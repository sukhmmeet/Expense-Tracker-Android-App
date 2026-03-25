package com.dhaliwal.expensetracker.presentation.app_ui.pie

data class Pies(val slices: List<Slice>) {
    fun totalSize(): Float = slices.map { it.value }.sum()
}