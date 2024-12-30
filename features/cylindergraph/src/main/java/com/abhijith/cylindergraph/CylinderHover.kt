package com.abhijith.cylindergraph

interface CylinderHover {
    fun onEnter()
    fun onMove(x: Float, y: Float, cylinderSectionData: CylinderSectionData)
    fun onExit()
}