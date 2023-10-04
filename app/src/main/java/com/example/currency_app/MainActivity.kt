package com.example.currency_app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.currency_app.databinding.ActivityMainBinding
import java.text.DecimalFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private val usdToNgn = 0.0013
    private val euroToNgn = 0.0012
    private val poundToNgn = 0.0011
    private val btcToNgn = 0.00000

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //accessing the convert button tip
        binding.convertCurrency.text = "convert"
        binding.convertCurrency.setOnClickListener { convert() }
    }

    private fun convert() {
        // in case there is no input from the user, we set the text to null
        // we can also use the elvis operator (?:)
        val userInput = binding.inputEdittext.text.toString().toDoubleOrNull()
        if (userInput == null) {
            binding.convertedAmount.text = ""
            return
        }

        //to make the conversion
        val conversion = when (binding.convertButton.checkedRadioButtonId) {
            R.id.dollars -> usdToNgn
            R.id.euros -> euroToNgn
            R.id.pounds -> poundToNgn
            else -> btcToNgn
        }
        //to calculate the conversion, use a var
        var calConversion = conversion * userInput

        // to check the roundup switch, we need to assign it to a variable
        val roundUp = binding.switchBtn.isChecked
        // we want to roundup and not down, so we use the Ceil function
        if (roundUp) {
            calConversion = ceil(calConversion)
        }




        // Format the result based on the selected currency
        val formattedResult = when (binding.convertButton.checkedRadioButtonId) {
            R.id.dollars, R.id.euros, R.id.pounds -> formatCurrency(calConversion)
            R.id.bitcoin -> formatBitcoin(calConversion)
            else -> ""
        }

        //val formattedResult = NumberFormat.getCurrencyInstance().format(tip)
        binding.convertedAmount.text = getString(R.string.amount, formattedResult)

    }

    private fun formatCurrency(amount: Double): String {
        val formatter = DecimalFormat("#,##0.00") // Adjust the pattern for your needs
        return formatter.format(amount)
    }

    private fun formatBitcoin(amount: Double): String {
        val formatter = DecimalFormat("#,##0.00000000") // Adjust the pattern for Bitcoin
        return formatter.format(amount)
    }



}