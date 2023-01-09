package com.example.conversordemedidas.models

import com.example.conversordemedidas.models.strategies.CalculationStrategy

data class CalculationStrategyHolder (
    val name : String,
    val calculationStrategy: CalculationStrategy
        )