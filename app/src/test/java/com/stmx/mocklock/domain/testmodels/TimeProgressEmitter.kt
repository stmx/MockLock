package com.stmx.mocklock.domain.testmodels

import com.stmx.mocklock.domain.entity.emitter.TimeProgressEmitter
import com.stmx.mocklock.domain.entity.calculator.TimeCalculator

fun linearTimeProgressEmitter(
    timeCalculator: TimeCalculator = polylineTimeCalculator()
): TimeProgressEmitter = TimeProgressEmitter.Linear(timeCalculator)