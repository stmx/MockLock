package com.stmx.mocklock.domain.entity.calculator

import com.stmx.mocklock.domain.entity.GeoPoint
import com.stmx.mocklock.domain.entity.equation.PositionEquation
import com.stmx.mocklock.domain.utils.integrate
import javax.inject.Inject

interface PositionCalculator {

    fun calculate(points: List<GeoPoint>, progress: Double): GeoPoint

    class Polyline @Inject constructor(
        private val distanceCalculator: DistanceCalculator,
        private val positionEquationCalculator: PositionEquationCalculator,
    ) : PositionCalculator {

        override fun calculate(points: List<GeoPoint>, progress: Double): GeoPoint {
            if (points.isEmpty()) error("PositionCalculator. Can't calculate position of empty list")
            val equation = getEquation(points, progress)
            val relativeProgress = getRelativeSectionProgress(points, progress)
            return equation.calculate(relativeProgress)
        }

        private fun getEquation(points: List<GeoPoint>, progress: Double): PositionEquation {
            val progressList = getProgressList(points)
            val section = progressList.indexOfFirst { it >= progress }
            val equations = positionEquationCalculator.calculate(points)
            return equations[section]
        }

        private fun getRelativeSectionProgress(points: List<GeoPoint>, progress: Double): Double {
            val progressList = getProgressList(points)
            val section = progressList.indexOfFirst { it >= progress }
            if (section == 0) return 0.0
            val start = progressList[section - 1]
            val end = progressList[section]
            return (progress - start) / (end - start)
        }

        private fun getProgressList(points: List<GeoPoint>): List<Double> {
            val totalDistance = distanceCalculator.calculateTotal(points)
            if (totalDistance == 0.0) return listOf(1.0)
            return distanceCalculator.calculate(points)
                .integrate()
                .map { distance -> distance / totalDistance }
        }
    }
}
