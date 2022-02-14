package com.stmx.mocklock.domain

fun List<Double>.integrate(offset: Double = 0.0): List<Double> {
    val result = mutableListOf<Double>()
    return this.mapIndexedTo(result) { index, current ->
        val tempLength = if (index == 0) offset else result[index - 1]
        tempLength + current
    }
}