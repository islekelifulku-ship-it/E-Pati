package com.example.e_pati

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StatisticsFragment : Fragment() {

    private lateinit var totalPetsText: TextView
    private lateinit var totalHealthText: TextView

    private lateinit var catCountText: TextView
    private lateinit var dogCountText: TextView
    private lateinit var birdCountText: TextView
    private lateinit var rodentCountText: TextView

    private lateinit var neuteredCountText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_statistics,
            container,
            false
        )

        totalPetsText =
            view.findViewById(R.id.totalPetsText)

        totalHealthText =
            view.findViewById(R.id.totalHealthText)

        catCountText =
            view.findViewById(R.id.catCountText)

        dogCountText =
            view.findViewById(R.id.dogCountText)

        birdCountText =
            view.findViewById(R.id.birdCountText)

        rodentCountText =
            view.findViewById(R.id.rodentCountText)

        neuteredCountText =
            view.findViewById(R.id.neuteredCountText)

        loadStatistics()

        return view
    }

    private fun loadStatistics() {

        val sharedPreferences =
            requireContext().getSharedPreferences(
                "pets",
                Context.MODE_PRIVATE
            )

        val json =
            sharedPreferences.getString(
                "pet_list",
                null
            )

        var petCount = 0

        var catCount = 0
        var dogCount = 0
        var birdCount = 0
        var rodentCount = 0

        if (json != null) {

            val type =
                object :
                    TypeToken<ArrayList<PetModel>>() {}.type

            val petList: ArrayList<PetModel> =
                Gson().fromJson(json, type)

            petCount = petList.size

            for (pet in petList) {

                if (
                    pet.petType.contains(
                        "Kedi",
                        ignoreCase = true
                    )
                ) {
                    catCount++
                }

                if (
                    pet.petType.contains(
                        "Köpek",
                        ignoreCase = true
                    )
                    ||
                    pet.petType.contains(
                        "Terrier",
                        ignoreCase = true
                    )
                ) {
                    dogCount++
                }

                if (
                    pet.petType.contains(
                        "Kuş",
                        ignoreCase = true
                    )
                ) {
                    birdCount++
                }

                if (
                    pet.petType.contains(
                        "Kemirgen",
                        ignoreCase = true
                    )
                ) {
                    rodentCount++
                }
            }
        }

        totalPetsText.text =
            petCount.toString()

        catCountText.text =
            catCount.toString()

        dogCountText.text =
            dogCount.toString()

        birdCountText.text =
            birdCount.toString()

        rodentCountText.text =
            rodentCount.toString()

        val healthPreferences =
            requireContext().getSharedPreferences(
                "health",
                Context.MODE_PRIVATE
            )

        val healthJson =
            healthPreferences.getString(
                "health_list",
                null
            )

        var healthCount = 0
        var neuteredCount = 0

        if (healthJson != null) {

            val healthType =
                object :
                    TypeToken<ArrayList<HealthModel>>() {}.type

            val healthList: ArrayList<HealthModel> =
                Gson().fromJson(
                    healthJson,
                    healthType
                )

            healthCount = healthList.size

            for (health in healthList) {

                if (
                    health.neutered.equals(
                        "Evet",
                        ignoreCase = true
                    )
                ) {
                    neuteredCount++
                }
            }
        }

        totalHealthText.text =
            healthCount.toString()

        neuteredCountText.text =
            neuteredCount.toString()
    }
}