package com.example.githubuser.ui.currencyconverter

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuser.R


class CurrencyConverter : AppCompatActivity() {
    private lateinit var fromCurrencySpinner: Spinner
    private lateinit var toCurrencySpinner: Spinner
    private lateinit var amountEditText: EditText
    private lateinit var convertButton: Button
    private lateinit var finalresult:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_converter)

        fromCurrencySpinner = findViewById(R.id.fromCurrencySpinner)
        toCurrencySpinner = findViewById(R.id.toCurrencySpinner)
        amountEditText = findViewById(R.id.amountEditText)
        convertButton = findViewById(R.id.convertButton)
        finalresult = findViewById(R.id.finalresult)
        // Create an ArrayAdapter using a string array and a default spinner layout
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.currencies_array,
            android.R.layout.simple_spinner_item
        )
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinners
        fromCurrencySpinner.adapter = adapter
        toCurrencySpinner.adapter = adapter

        convertButton.setOnClickListener {
            val amount = amountEditText.text.toString().toDoubleOrNull()
            if (amount != null) {
                val fromCurrency = fromCurrencySpinner.selectedItem.toString()
                val toCurrency = toCurrencySpinner.selectedItem.toString()
                val convertedAmount = convertCurrency(amount, fromCurrency, toCurrency)
                finalresult.text = "$amount $fromCurrency = $convertedAmount $toCurrency"
            } else {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show()
                finalresult.text = "Invalid amount"
            }
        }
    }

    private fun convertCurrency(amount: Double, fromCurrency: String, toCurrency: String): Double {
        // Implement your currency conversion logic here
        // You can use the previously provided 'convertCurrency' and 'getExchangeRate' functions
        return amount * getExchangeRate(fromCurrency, toCurrency)
    }

    private fun getExchangeRate(fromCurrency: String, toCurrency: String): Double {
        // Implement your exchange rate retrieval logic here
        // You can use the previously provided 'getExchangeRate' function or fetch rates from an API
        // Remember to handle error cases and provide appropriate exchange rates
        // For simplicity, you can use a fixed rate for now
        return when (fromCurrency) {
            "USD" -> when (toCurrency) {
                "EUR" -> 0.85
                "GBP" -> 0.72
                else -> 1.0 // Default case
            }
            "EUR" -> when (toCurrency) {
                "USD" -> 1.18
                "GBP" -> 0.85
                else -> 1.0
            }
            "GBP" -> when (toCurrency) {
                "USD" -> 1.39
                "EUR" -> 1.17
                else -> 1.0
            }
            else -> 1.0
        }
    }
}
