package com.example.conversordemedidas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.conversordemedidas.models.CalculationStrategyHolder
import com.example.conversordemedidas.models.Calculator
import com.example.conversordemedidas.models.strategies.MeterToKilometerStrategy
import com.example.conversordemedidas.models.strategies.MetersToCentimetersStrategy
import com.example.conversordemedidas.models.strategies.kilometerToCentimeters
import com.example.conversordemedidas.models.strategies.kilometerToMeterStrategy
import kotlin.coroutines.cancellation.CancellationException

class MainActivity : AppCompatActivity() {

    private val supportedCalculationStrategies = arrayOf(
        CalculationStrategyHolder("Quilometros para centímetros", kilometerToCentimeters()),
        CalculationStrategyHolder("Quilometro para metros", kilometerToMeterStrategy()),
        CalculationStrategyHolder("Metros para centímetros", MetersToCentimetersStrategy()),
        CalculationStrategyHolder("Metros para quilometros", MeterToKilometerStrategy())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val spConvertions : Spinner = findViewById(R.id.spConvertions)
        val spAdapter = ArrayAdapter(this, R.layout.res_spinner_item, getSpinnerData())
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spConvertions.adapter = spAdapter


    }

    fun getSpinnerData(): MutableList<String> {
        val spinnerData = mutableListOf<String>()

        supportedCalculationStrategies.forEach{
            spinnerData.add(it.name)
        }
        return spinnerData
    }
}