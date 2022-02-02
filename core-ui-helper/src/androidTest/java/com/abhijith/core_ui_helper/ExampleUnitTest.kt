package com.abhijith.core_ui_helper

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest2 {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun isRectDistributionIsCorrectWithOnlyOneData() {
        val viewHelper = object : ViewHelper {
            override val tempBound: Rect = Rect()
            override val paint: Paint = Paint()
            override val canvasBound: Rect = Rect()
        }
        val list = listOf<SectionData>(
            SectionData(50, Color.RED),
            SectionData(50, Color.RED),
        )

        val rect = Rect(0, 0, 1000, 1000)
        rect.getSpaceDistributed(
            viewHelper,
            list,
        ) { i, rect, sectionData ->
            if (i == 0)
                assert(rect == Rect(0, 0, 1000, 500)) {
                    "Curent $rect"
                }
            if (i == 1)
                assert(rect == Rect(0, 500, 1000, 1000)) {
                    "Curent $rect"
                }
        }
    }
}