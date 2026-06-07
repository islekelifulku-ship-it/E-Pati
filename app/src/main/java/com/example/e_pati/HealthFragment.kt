package com.example.e_pati

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HealthFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var healthList: ArrayList<HealthModel>
    private lateinit var healthAdapter: HealthAdapter

    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_health,
            container,
            false
        )

        recyclerView =
            view.findViewById(R.id.healthRecyclerView)

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        healthList = ArrayList()

        loadHealth()

        if (healthList.isEmpty()) {

            healthList.add(
                HealthModel(
                    "Bunny",
                    "8 KG",
                    "Vitamin Kullanıyor",
                    "18 Mayıs",
                    "Hayır"
                )
            )

            saveHealth()
        }

        healthAdapter = HealthAdapter(
            healthList
        ) {
            saveHealth()
        }

        recyclerView.adapter = healthAdapter

        val addButton =
            view.findViewById<MaterialButton>(
                R.id.addHealthButton
            )

        addButton.setOnClickListener {

            val dialogView =
                layoutInflater.inflate(
                    R.layout.dialog_add_health,
                    null
                )

            val petNameEdit =
                dialogView.findViewById<EditText>(
                    R.id.petNameEditText
                )

            val weightEdit =
                dialogView.findViewById<EditText>(
                    R.id.weightEditText
                )

            val vaccineEdit =
                dialogView.findViewById<EditText>(
                    R.id.vaccineEditText
                )

            val vetEdit =
                dialogView.findViewById<EditText>(
                    R.id.vetEditText
                )

            val neuteredSpinner =
                dialogView.findViewById<Spinner>(
                    R.id.neuteredSpinner
                )

            val neuteredOptions = arrayOf(
                "Evet",
                "Hayır"
            )

            neuteredSpinner.adapter =
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    neuteredOptions
                )

            AlertDialog.Builder(requireContext())
                .setTitle("Sağlık Kaydı Ekle")
                .setView(dialogView)
                .setPositiveButton("Kaydet") { _, _ ->

                    healthList.add(
                        HealthModel(
                            petNameEdit.text.toString(),
                            weightEdit.text.toString(),
                            vaccineEdit.text.toString(),
                            vetEdit.text.toString(),
                            neuteredSpinner.selectedItem.toString()
                        )
                    )

                    healthAdapter.notifyItemInserted(
                        healthList.size - 1
                    )

                    saveHealth()
                }
                .setNegativeButton(
                    "İptal",
                    null
                )
                .show()
        }

        return view
    }

    private fun saveHealth() {

        val sharedPreferences =
            requireContext().getSharedPreferences(
                "health",
                Context.MODE_PRIVATE
            )

        val editor = sharedPreferences.edit()

        val json = gson.toJson(healthList)

        editor.putString(
            "health_list",
            json
        )

        editor.apply()
    }

    private fun loadHealth() {

        val sharedPreferences =
            requireContext().getSharedPreferences(
                "health",
                Context.MODE_PRIVATE
            )

        val json =
            sharedPreferences.getString(
                "health_list",
                null
            )

        if (json != null) {

            val type =
                object :
                    TypeToken<ArrayList<HealthModel>>() {}.type

            healthList =
                gson.fromJson(
                    json,
                    type
                )
        }
    }
}