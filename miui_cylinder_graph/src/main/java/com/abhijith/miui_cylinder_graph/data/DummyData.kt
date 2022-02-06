package com.abhijith.miui_cylinder_graph.data

import com.abhijith.miui_cylinder_graph.R
import com.abhijith.miui_cylinder_graph.model.SectionData
import kotlin.math.roundToInt

object DummyData {
    val items = listOf(
        SectionData(
            "1",
            50,
            R.color.c_lavender
        ),
        SectionData(
            "2",
            25,
            R.color.c_saffron
        ),
        SectionData(
            "3",
            12,
            R.color.c_pale_cornflower_blue
        ),
        SectionData(
            "4",
            6,
            R.color.c_sana
        ),
        SectionData(
            "5",
            3,
            R.color.c_emerald
        ),
        SectionData(
            "6",
            2,
            R.color.c_vernilion
        ),
        SectionData(
            "7",
            1,
            R.color.c_united_nations_blue
        ),
    ).asReversed()
}

//val delta: Int = (rect.height() * 0.3).roundToInt()
/*if (index == 0) {
    rect.top = rect.top - (delta / 3)
    oval = Rect(0, rect.top - delta, width, rect.top + delta)
}
rect.top = rect.top - shrinkY
rect.bottom = rect.bottom + shrinkY*/