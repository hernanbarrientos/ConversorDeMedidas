package com.example.conversordemedidas.models

import com.example.conversordemedidas.models.strategies.CalculationStrategy

object Calculator {

    private var calculationStrategy : CalculationStrategy? = null

    fun setCalculationStrategy(calculationStrategy: CalculationStrategy) {
        this.calculationStrategy = calculationStrategy
    }

    fun calculate(value: Double): Double{

        if(calculationStrategy == null)
            throw java.lang.IllegalArgumentException("Calculation Strategy is not set")


        return calculationStrategy!!.calculate(value)

    }
}