package com.example.conversordemedidas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.conversordemedidas.models.CalculationStrategyHolder
import com.example.conversordemedidas.models.Calculator
import com.example.conversordemedidas.models.strategies.MeterToKilometerStrategy
import com.example.conversordemedidas.models.strategies.MetersToCentimetersStrategy
import com.example.conversordemedidas.models.strategies.kilometerToCentimeters
import com.example.conversordemedidas.models.strategies.kilometerToMeterStrategy
import kotlin.coroutines.cancellation.CancellationException

class MainActivity : AppCompatActivity() {

    private lateinit var edtValue: EditText
    private lateinit var spConvertions: Spinner
    private val supportedCalculationStrategies = arrayOf(
        CalculationStrategyHolder("Quilometros para centímetros", kilometerToCentimeters()),
        CalculationStrategyHolder("Quilometro para metros", kilometerToMeterStrategy()),
        CalculationStrategyHolder("Metros para centímetros", MetersToCentimetersStrategy()),
        CalculationStrategyHolder("Metros para quilometros", MeterToKilometerStrategy())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var value = 0.0
        var position = 0

        savedInstanceState?.let{
            value = it.getDouble("Value")
            position= it.getInt("POSITION")
        }

        initUi()

        setUi(value, position)

        setAction()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        try{
            outState.putDouble("VALUE", edtValue.text.toString().toDouble())
        }catch(e : NumberFormatException){
            outState.putInt("POSITION", spConvertions.selectedItemPosition)
        }
    }

    private fun initUi() {
        spConvertions = findViewById(R.id.spConvertions)
        edtValue = findViewById(R.id.edtValue)
    }

    private fun setAction() {
        val btnConvert: Button = findViewById(R.id.btnConvert)


        btnConvert.setOnClickListener{

            try {

                val value = edtValue.text.toString().toDouble()
                val calculationStrategy = supportedCalculationStrategies[
                        spConvertions.selectedItemPosition
                ].calculationStrategy


               Calculator.setCalculationStrategy(
                    calculationStrategy
                )

                val result = Calculator.calculate(value)
                val label = calculationStrategy.getResultLabel(result != 1.toDouble())

                showResult(result, label)



            }catch (e:java.lang.NumberFormatException){
             edtValue.error = "Valor inválido"
             edtValue.requestFocus()
            }

        }

        val btnClean : Button = findViewById(R.id.btnClean)
        btnClean.setOnClickListener {
            clean()
        }

    }

    private fun clean() {
        edtValue.setText("")
        edtValue.error = null

        spConvertions.setSelection(0)
    }

    private fun showResult(result: Double, label: String) {

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("RESULT", result)
        intent.putExtra("LABEL", label)


        startActivity(intent)

    }

    private fun setUi(value: Double, position: Int) {

        if(value == 0.0) {
            clean()
        }else{
            edtValue.setText(value.toString())
        }



        val spAdapter = ArrayAdapter(this, R.layout.res_spinner_item, getSpinnerData())
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spConvertions.adapter = spAdapter
        spConvertions.setSelection(position)
    }


    private fun getSpinnerData(): MutableList<String> {
        val spinnerData = mutableListOf<String>()

        supportedCalculationStrategies.forEach{
            spinnerData.add(it.name)
        }
        return spinnerData
    }
}