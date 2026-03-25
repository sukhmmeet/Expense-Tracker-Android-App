package com.dhaliwal.expensetracker.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import com.dhaliwal.expensetracker.data.local.Expense
import java.io.File
import java.io.FileOutputStream

@SuppressLint("SimpleDateFormat")
fun generatePdf(context: Context, expenses: List<Expense>): File {

    val pdfDocument = PdfDocument()

    val pageWidth = 595
    val pageHeight = 842
    var pageNumber = 1

    var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
    var page = pdfDocument.startPage(pageInfo)

    var canvas = page.canvas
    val paint = Paint()

    paint.textSize = 14f

    var y = 50

    canvas.drawText("Expense Report", 200f, y.toFloat(), paint)

    y += 40

    // Table Header
    paint.textSize = 10f

    canvas.drawText("Title", 20f, y.toFloat(), paint)
    canvas.drawText("Amount", 120f, y.toFloat(), paint)
    canvas.drawText("Category", 200f, y.toFloat(), paint)
    canvas.drawText("Date", 300f, y.toFloat(), paint)
    canvas.drawText("Type", 380f, y.toFloat(), paint)
    canvas.drawText("Payment", 450f, y.toFloat(), paint)

    y += 25

    for (expense in expenses) {

        if (y > pageHeight - 50) {

            pdfDocument.finishPage(page)

            pageNumber++

            pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
            page = pdfDocument.startPage(pageInfo)

            canvas = page.canvas
            y = 50
        }

        val dateString = java.text.SimpleDateFormat("dd MMM yyyy")
            .format(java.util.Date(expense.date))

        canvas.drawText(expense.title, 20f, y.toFloat(), paint)
        canvas.drawText("₹${expense.amount}", 120f, y.toFloat(), paint)
        canvas.drawText(expense.category, 200f, y.toFloat(), paint)
        canvas.drawText(dateString, 300f, y.toFloat(), paint)
        canvas.drawText(expense.type, 380f, y.toFloat(), paint)
        canvas.drawText(expense.payment_method, 450f, y.toFloat(), paint)

        y += 20
    }

    pdfDocument.finishPage(page)

    val file = File(context.cacheDir, "expenses_report.pdf")

    pdfDocument.writeTo(FileOutputStream(file))
    pdfDocument.close()

    return file
}
fun sharePdf(context: Context, file: File) {

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )

    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "application/pdf"
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

    context.startActivity(
        Intent.createChooser(intent, "Share PDF")
    )
}