package com.example.e_pati

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CalendarFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var appointmentList: ArrayList<AppointmentModel>
    private lateinit var appointmentAdapter: AppointmentAdapter

    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view =
            inflater.inflate(
                R.layout.fragment_calendar,
                container,
                false
            )

        recyclerView =
            view.findViewById(
                R.id.appointmentRecyclerView
            )

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        appointmentList = ArrayList()

        loadAppointments()

        appointmentAdapter =
            AppointmentAdapter(
                appointmentList
            ) {
                saveAppointments()
            }

        recyclerView.adapter =
            appointmentAdapter

        val addButton =
            view.findViewById<MaterialButton>(
                R.id.addAppointmentButton
            )

        addButton.setOnClickListener {

            val dialogView =
                layoutInflater.inflate(
                    R.layout.dialog_add_appointment,
                    null
                )

            val petNameEdit =
                dialogView.findViewById<EditText>(
                    R.id.petNameEditText
                )

            val typeEdit =
                dialogView.findViewById<EditText>(
                    R.id.typeEditText
                )

            val dateEdit =
                dialogView.findViewById<EditText>(
                    R.id.dateEditText
                )

            AlertDialog.Builder(
                requireContext()
            )
                .setTitle("Randevu Ekle")
                .setView(dialogView)
                .setPositiveButton("Kaydet") { _, _ ->

                    appointmentList.add(
                        AppointmentModel(
                            petNameEdit.text.toString(),
                            typeEdit.text.toString(),
                            dateEdit.text.toString()
                        )
                    )

                    appointmentAdapter.notifyDataSetChanged()

                    android.widget.Toast.makeText(
                        requireContext(),
                        "Liste boyutu: ${appointmentList.size}",
                        android.widget.Toast.LENGTH_LONG
                    ).show()
                    saveAppointments()
                    val intent =
                        android.content.Intent(
                            requireContext(),
                            NotificationReceiver::class.java
                        )

                    intent.putExtra(
                        "message",
                        "${petNameEdit.text} için ${typeEdit.text} randevusu oluşturuldu 📅"
                    )

                    NotificationReceiver().onReceive(
                        requireContext(),
                        intent
                    )
                }
                .setNegativeButton(
                    "İptal",
                    null
                )
                .show()
        }

        return view
    }

    private fun saveAppointments() {


        val sharedPreferences =
            requireContext().getSharedPreferences(
                "appointments",
                Context.MODE_PRIVATE
            )

        val editor =
            sharedPreferences.edit()

        editor.putString(
            "appointment_list",
            gson.toJson(appointmentList)
        )

        editor.apply()
    }

    private fun loadAppointments() {

        val sharedPreferences =
            requireContext().getSharedPreferences(
                "appointments",
                Context.MODE_PRIVATE
            )

        val json =
            sharedPreferences.getString(
                "appointment_list",
                null
            )

        if (json != null) {

            val type =
                object :
                    TypeToken<ArrayList<AppointmentModel>>() {}.type

            appointmentList =
                gson.fromJson(
                    json,
                    type
                )
        }
    }
}