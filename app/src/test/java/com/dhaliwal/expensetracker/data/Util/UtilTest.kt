package com.dhaliwal.expensetracker.data.Util

import com.dhaliwal.expensetracker.R
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UtilTest {

    lateinit var util : Util

    @Before
    fun setUp() {
        util = Util()
    }

    @Test
    fun getLogo() {
        assertEquals(R.drawable.food, util.getLogo("Food"))
    }

//    @After
//    fun tearDown() {
//        TODO("Not yet implemented")
//    }

    @Test
    fun convertMillisToDate() {
        val ans = util.convertMillisToDate(1704067200000)
        assertEquals("01 Jan 2024" , ans)
    }

}