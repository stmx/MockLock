package com.stmx.mocklock.domain.entity

interface Mapper<T, R> {
    fun map(data: T): R
}
