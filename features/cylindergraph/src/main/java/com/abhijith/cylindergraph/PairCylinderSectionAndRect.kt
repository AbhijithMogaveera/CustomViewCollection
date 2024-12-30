package com.abhijith.cylindergraph

import android.graphics.Rect

data class PairCylinderSectionAndRect(
    val data: CylinderSectionData,
    val rect: Rect = Rect(),
)