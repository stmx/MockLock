package com.stmx.mocklock.domain.testmodels

import com.stmx.mocklock.domain.TimeProgressEmitter
import com.stmx.mocklock.domain.entity.calculator.TimeCalculator

private const val DEFAULT_PERIOD = 1000L

fun linearTimeProgressEmitter(
    timeCalculator: TimeCalculator = polylineTimeCalculator(),
    period: Long = DEFAULT_PERIOD
): TimeProgressEmitter = TimeProgressEmitter.Linear(timeCalculator, period)