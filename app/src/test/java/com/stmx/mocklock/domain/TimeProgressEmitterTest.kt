package com.stmx.mocklock.domain

import com.stmx.mocklock.domain.testmodels.linearTimeProgressEmitter
import com.stmx.mocklock.domain.testmodels.timeCalculatorStub
import com.stmx.mocklock.domain.testmodels.trackConfiguration
import kotlin.math.ceil
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
class TimeProgressEmitterTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun `test results for zero total time`() = scope.runTest {
        launch { assertValueEquals(0L, 1000L) }
    }

    @Test
    fun `test results for different period`() = scope.runTest {
        launch { assertValueEquals(3000 * 1L, 1000L) }
        launch { assertValueEquals(3000 * 1L, 1100L) }
        launch { assertValueEquals(3000 * 1L, 1500L) }
        launch { assertValueEquals(3000 * 1L, 1786L) }
        launch { assertValueEquals(3000 * 1L, 3000L) }
        launch { assertValueEquals(3000 * 1L, 3500L) }
    }

    @Test
    fun `test results for different total time`() = scope.runTest {
        launch { assertValueEquals(5000 * 1L, 1000L) }
        launch { assertValueEquals(4123 * 1L, 1000L) }
        launch { assertValueEquals(3412 * 1L, 1000L) }
        launch { assertValueEquals(2345 * 1L, 1000L) }
        launch { assertValueEquals(1234 * 1L, 1000L) }
        launch { assertValueEquals(1000 * 1L, 1000L) }
        launch { assertValueEquals(800 * 1L, 1000L) }
    }

    @Test
    fun `test period time for different total time`() = scope.runTest {
        launch { assertPeriodEquals(4000 * 1L, 4100L) }
        launch { assertPeriodEquals(4000 * 1L, 1600L) }
        launch { assertPeriodEquals(4000 * 1L, 1000L) }
        launch { assertPeriodEquals(4000 * 1L, 800L) }
    }


    private suspend fun assertValueEquals(time: Long, period: Long) = withContext(Dispatchers.IO) {
        val progressEmitter = linearTimeProgressEmitter(timeCalculatorStub(time))
        progressEmitter.setMinimalPeriodUpdate(period)
        val results = progressEmitter.start(trackConfiguration()).toList()
        val size = ceil(time.toDouble() / period).toInt()
        for (index in 0..size) {
            val expected = if (index == size) 1.0 else index * period.toDouble() / time
            assertEquals(
                expected = expected,
                actual = results[index],
                absoluteTolerance = DELTA_PERCENT
            )
        }
    }

    private suspend fun assertPeriodEquals(time: Long, period: Long) = withContext(Dispatchers.IO) {
        val progressEmitter = linearTimeProgressEmitter(timeCalculatorStub(time))
        progressEmitter.setMinimalPeriodUpdate(period)
        var prevTime = System.currentTimeMillis()
        val expectedPeriod = period.coerceAtMost(time).toDouble()
        progressEmitter.start(trackConfiguration()).collect { progress ->
            val actualPeriod =
                if (progress == 0.0) expectedPeriod else System.currentTimeMillis() - prevTime
            prevTime = System.currentTimeMillis()
            assertEquals(
                expected = expectedPeriod,
                actual = actualPeriod.toDouble(),
                absoluteTolerance = DELTA_TIME_MS
            )

        }
    }
}