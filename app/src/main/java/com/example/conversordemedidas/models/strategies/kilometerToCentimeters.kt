package com.example.conversordemedidas.models.strategies

class kilometerToCentimeters : CalculationStrategy {
    override fun calculate(value: Double): Double {
        return value * 100_000
    }

    override fun getResultLabel(isPlural: Boolean): String = if(isPlural) "centímetros" else "centimetro"
}