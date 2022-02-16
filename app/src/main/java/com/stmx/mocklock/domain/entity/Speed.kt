package com.stmx.mocklock.domain.entity

interface Speed {
    val ms: Double
    val kmh: Double

    class KmH(value: Double) : Speed {
        override val ms: Double = value * RATIO_TO_MS
        override val kmh: Double = value

        companion object {
            private const val RATIO_TO_MS = 0.277778
        }
    }

    class MS(value: Double) : Speed {
        override val ms: Double = value
        override val kmh: Double = value * RATIO_TO_KMH

        companion object {
            private const val RATIO_TO_KMH = 3.6
        }
    }

    object Zero : Speed {
        override val ms: Double = 0.0
        override val kmh: Double = 0.0
    }
}
