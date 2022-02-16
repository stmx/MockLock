package com.stmx.mocklock.domain.testmodels

import com.stmx.mocklock.domain.entity.calculator.DistanceCalculator
import com.stmx.mocklock.domain.entity.calculator.PositionCalculator
import com.stmx.mocklock.domain.entity.calculator.PositionEquationCalculator

fun polylinePositionCalculator(
    distanceCalculator: DistanceCalculator = polylineDistanceCalculator(),
    positionEquationCalculator: PositionEquationCalculator = parametricPositionEquationCalculator()
): PositionCalculator = PositionCalculator.Polyline(distanceCalculator, positionEquationCalculator)