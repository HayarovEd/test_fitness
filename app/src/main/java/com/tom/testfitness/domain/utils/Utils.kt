package com.tom.testfitness.domain.utils

import com.tom.testfitness.domain.model.TypeFitness

fun Int.convertToType(): TypeFitness {
    return when (this) {
        1 -> TypeFitness.TRENING
        2 -> TypeFitness.AIR
        3 -> TypeFitness.COMPLEX
        else -> TypeFitness.TRENING
    }
}