package com.stmx.mocklock.data.entity

class GeoPolyLine(private val geoLines: List<GeoLine>) {

    init {
        require(geoLines.isNotEmpty())
    }

    private val totalLength: Double = geoLines.sumOf { line -> line.length }

    fun toNormalizedLengths(): List<Double> {
        return geoLines.map { it.length / totalLength }
    }

    fun toParametricEquations(): List<ParametricEquation> {
        return geoLines.mapTo(ArrayList(geoLines.size)) { geoLine ->
            val kx = geoLine.start.latitude - geoLine.end.latitude
            val ky = geoLine.start.longitude - geoLine.end.longitude
            val bx = geoLine.end.latitude
            val by = geoLine.end.longitude
            ParametricEquation(kx, ky, bx, by)
        }
    }
}