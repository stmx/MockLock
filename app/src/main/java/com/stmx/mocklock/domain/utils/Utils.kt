package com.stmx.mocklock.domain.utils

import com.stmx.mocklock.domain.entity.Mapper

fun List<Double>.integrate(offset: Double = 0.0): List<Double> {
    val result = mutableListOf<Double>()
    return this.mapIndexedTo(result) { index, current ->
        val tempLength = if (index == 0) offset else result[index - 1]
        tempLength + current
    }
}

inline fun <reified T, reified R> Mapper<T, R>.mapNullable(data: T?): R? {
    return if (data == null) null
    else this.map(data)
}
