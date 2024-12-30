package com.abhijith.cylindergraph

import com.abhijith.miui_cylinder_graph.R

object DemoData {
    val items = listOf(
        CylinderSectionData(
            50,
            R.color.c_lavender
        ),
        CylinderSectionData(
            25,
            R.color.c_saffron
        ),
        CylinderSectionData(
            12,
            R.color.c_pale_cornflower_blue
        ),
        CylinderSectionData(
            6,
            R.color.c_sana
        ),
        CylinderSectionData(
            3,
            R.color.c_emerald
        ),
        CylinderSectionData(
            2,
            R.color.c_vernilion
        ),
        CylinderSectionData(
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