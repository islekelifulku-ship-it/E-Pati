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

class PetsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var petList: ArrayList<PetModel>
    private lateinit var petAdapter: PetAdapter

    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.fragment_pets,
            container,
            false
        )

        recyclerView =
            view.findViewById(R.id.petRecyclerView)

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        petList = ArrayList()

        loadPets()

        if (petList.isEmpty()) {

            petList.add(
                PetModel(
                    "Bunny",
                    "Maltese Terrier Köpek",
                    R.drawable.kopek
                )
            )

            savePets()
        }

        petAdapter = PetAdapter(
            petList
        ) {
            savePets()
        }

        recyclerView.adapter = petAdapter

        val addButton =
            view.findViewById<MaterialButton>(
                R.id.addPetButton
            )

        addButton.setOnClickListener {

            val dialogView =
                layoutInflater.inflate(
                    R.layout.dialog_add_pet,
                    null
                )

            val petNameEdit =
                dialogView.findViewById<EditText>(
                    R.id.petNameEditText
                )

            val petTypeSpinner =
                dialogView.findViewById<Spinner>(
                    R.id.petTypeSpinner
                )

            val petTypes = arrayOf(
                "Köpek",
                "Kedi",
                "Cins Kedi",
                "Kuş",
                "Kemirgen"
            )

            petTypeSpinner.adapter =
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    petTypes
                )

            AlertDialog.Builder(requireContext())
                .setTitle("Yeni Evcil Hayvan")
                .setView(dialogView)
                .setPositiveButton("Ekle") { _, _ ->

                    val selectedType =
                        petTypeSpinner.selectedItem.toString()

                    val imageRes = when (selectedType) {

                        " Köpek" ->
                            R.drawable.kopek

                        " Kedi" ->
                            R.drawable.zulfu

                        "Cins Kedi" ->
                            R.drawable.british

                        "Kuş" ->
                            R.drawable.kus

                        "Kemirgen" ->
                            R.drawable.k

                        else ->
                            R.drawable.kopek
                    }

                    petList.add(
                        PetModel(
                            petNameEdit.text.toString(),
                            selectedType,
                            imageRes
                        )
                    )

                    petAdapter.notifyDataSetChanged()

                    savePets()
                }
                .setNegativeButton(
                    "İptal",
                    null
                )
                .show()
        }

        return view
    }

    private fun savePets() {

        val sharedPreferences =
            requireContext().getSharedPreferences(
                "pets",
                Context.MODE_PRIVATE
            )

        val editor = sharedPreferences.edit()

        val json = gson.toJson(petList)

        editor.putString(
            "pet_list",
            json
        )

        editor.apply()
    }

    private fun loadPets() {

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

        if (json != null) {

            val type =
                object :
                    TypeToken<ArrayList<PetModel>>() {}.type

            petList =
                gson.fromJson(
                    json,
                    type
                )
        }
    }
}