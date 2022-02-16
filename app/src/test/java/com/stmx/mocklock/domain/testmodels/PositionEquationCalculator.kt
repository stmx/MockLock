package com.stmx.mocklock.domain.testmodels

import com.stmx.mocklock.domain.entity.calculator.PositionEquationCalculator

fun parametricPositionEquationCalculator(): PositionEquationCalculator =
    PositionEquationCalculator.Parametric()