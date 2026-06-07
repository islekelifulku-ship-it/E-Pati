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
import android.content.Intent

class HomeFragment : Fragment() {

    private lateinit var appointment1Text: TextView
    private lateinit var appointment2Text: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view =
            inflater.inflate(
                R.layout.fragment_home,
                container,
                false
            )

        appointment1Text =
            view.findViewById(
                R.id.appointment1Text
            )

        appointment2Text =
            view.findViewById(
                R.id.appointment2Text
            )

        loadAppointments()
        sendTestNotification()

        return view
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

            val appointmentList:
                    ArrayList<AppointmentModel> =
                Gson().fromJson(
                    json,
                    type
                )

            if (appointmentList.isNotEmpty()) {

                appointment1Text.text =
                    "📅 ${appointmentList[0].petName} - ${appointmentList[0].appointmentType}\n${appointmentList[0].appointmentDate}"
            }

            if (appointmentList.size > 1) {

                appointment2Text.text =
                    "📅 ${appointmentList[1].petName} - ${appointmentList[1].appointmentType}\n${appointmentList[1].appointmentDate}"
            }
        }
    }
    private fun sendTestNotification() {

        val intent = Intent(
            requireContext(),
            NotificationReceiver::class.java
        )

        intent.putExtra(
            "message",
            "PetCare bildirim sistemi çalışıyor 🎉"
        )

        NotificationReceiver().onReceive(
            requireContext(),
            intent
        )
    }
}