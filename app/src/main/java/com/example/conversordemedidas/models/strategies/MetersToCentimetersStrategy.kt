package com.example.conversordemedidas.models.strategies

class MetersToCentimetersStrategy : CalculationStrategy {
    override fun calculate(value: Double): Double {
        return value * 100
    }

    override fun getResultLabel(isPlural: Boolean): String = if(isPlural) "centímetros" else "centímetro"
}