package com.example.e_pati

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import kotlin.math.pow

class CalorieFragment : Fragment() {

    private lateinit var weightEditText: EditText
    private lateinit var caloriePerGramEditText: EditText
    private lateinit var givenFoodEditText: EditText

    private lateinit var goalSpinner: Spinner
    private lateinit var neuteredSpinner: Spinner

    private lateinit var calculateButton: MaterialButton
    private lateinit var resultText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_calorie,
            container,
            false
        )

        weightEditText =
            view.findViewById(R.id.weightEditText)

        caloriePerGramEditText =
            view.findViewById(R.id.caloriePerGramEditText)

        givenFoodEditText =
            view.findViewById(R.id.givenFoodEditText)

        goalSpinner =
            view.findViewById(R.id.goalSpinner)

        neuteredSpinner =
            view.findViewById(R.id.neuteredSpinner)

        calculateButton =
            view.findViewById(R.id.calculateButton)

        resultText =
            view.findViewById(R.id.resultText)

        goalSpinner.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayOf(
                    "Kilo Koruma",
                    "Kilo Verme",
                    "Kilo Alma"
                )
            )

        neuteredSpinner.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayOf(
                    "Kısırlaştırıldı",
                    "Kısırlaştırılmadı"
                )
            )

        calculateButton.setOnClickListener {
            calculateCalories()
        }

        return view
    }

    private fun calculateCalories() {

        val weight =
            weightEditText.text.toString()
                .toDoubleOrNull()
                ?: return

        val kcalPerGram =
            caloriePerGramEditText.text.toString()
                .toDoubleOrNull()
                ?: return

        val rer =
            70 * weight.pow(0.75)

        var calories = rer

        if (
            neuteredSpinner.selectedItem.toString()
            == "Kısırlaştırıldı"
        ) {
            calories *= 1.2
        } else {
            calories *= 1.4
        }

        when (
            goalSpinner.selectedItem.toString()
        ) {

            "Kilo Verme" -> {
                calories *= 0.8
            }

            "Kilo Alma" -> {
                calories *= 1.2
            }
        }

        val grams =
            calories / kcalPerGram

        val givenFood =
            givenFoodEditText.text.toString()
                .toDoubleOrNull()
                ?: 0.0

        val remainingFood =
            (grams - givenFood).coerceAtLeast(0.0)

        resultText.text =
            "🔥 Günlük Kalori: ${calories.toInt()} kcal\n\n" +
                    "🍖 Günlük Mama: ${grams.toInt()} gram\n\n" +
                    "✅ Verilen Mama: ${givenFood.toInt()} gram\n\n" +
                    "📦 Kalan Mama: ${remainingFood.toInt()} gram"
    }
}