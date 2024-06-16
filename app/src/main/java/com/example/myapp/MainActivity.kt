package com.example.myapp


import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var operationTextView: TextView
    private lateinit var resultTextView: TextView

    private var operation = ""
    private var firstNumber = ""
    private var secondNumber = ""
    private var currentOperation = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        operationTextView = findViewById(R.id.textView2)
        resultTextView = findViewById(R.id.textView3)

        val buttonIds = listOf(
            R.id.B0, R.id.B1, R.id.B2, R.id.B3, R.id.B4, R.id.B5, R.id.B6, R.id.B7, R.id.B8, R.id.B9,
            R.id.Bplus, R.id.Bminus, R.id.Bmultiplcation, R.id.Bdivision, R.id.Bequal, R.id.AC
        )

        val buttons = buttonIds.map { findViewById<Button>(it) }

        for (button in buttons) {
            button.setOnClickListener {
                onButtonClick(button)
            }
        }
    }

    private fun onButtonClick(button: Button) {
        when (button.id) {
            // Handling numeric buttons
            R.id.B0, R.id.B1, R.id.B2, R.id.B3, R.id.B4, R.id.B5, R.id.B6, R.id.B7, R.id.B8, R.id.B9 -> {
                if (currentOperation.isEmpty()) {
                    firstNumber += button.text
                    operation = firstNumber
                } else {
                    secondNumber += button.text
                    operation = "$firstNumber $currentOperation $secondNumber"
                }
                operationTextView.text = operation
            }
            // Handling operator buttons
            R.id.Bplus, R.id.Bminus, R.id.Bmultiplcation, R.id.Bdivision -> {
                if (firstNumber.isNotEmpty()) {
                    if (secondNumber.isNotEmpty()) {
                        // If both numbers are entered, calculate the intermediate result
                        val result = calculateResult()
                        firstNumber = result // Set the result as the first number for further operations
                        secondNumber = "" // Clear the second number
                    }
                    currentOperation = button.text.toString()
                    operation = "$firstNumber $currentOperation"
                    operationTextView.text = operation
                }
            }
            // Handling equals button
            R.id.Bequal -> {
                if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty() && currentOperation.isNotEmpty()) {
                    // Calculate the final result when equals button is clicked
                    val result = calculateResult()
                    resultTextView.text = result
                    operationTextView.text = operation
                    resetCalculator()
                }
            }
            // Handling AC button
            R.id.AC -> {
                resetCalculator()
                operationTextView.text = ""
                resultTextView.text = ""
            }
        }
    }


    private fun calculateResult(): String {
        return try {
            val num1 = firstNumber.toDouble()
            val num2 = secondNumber.toDouble()
            when (currentOperation) {
                "+" -> (num1 + num2).toString()
                "-" -> (num1 - num2).toString()
                "x" -> (num1 * num2).toString()
                "÷" -> {
                    if (num2 != 0.0) (num1 / num2).toString()
                    else "Error"
                }
                else -> "Error"
            }
        } catch (e: Exception) {
            "Error"
        }
    }



    private fun resetCalculator() {
        operation = ""
        firstNumber = ""
        secondNumber = ""
        currentOperation = ""
    }
}
