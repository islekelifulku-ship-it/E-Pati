package com.example.e_pati

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class AppointmentAdapter(
    private val appointmentList: ArrayList<AppointmentModel>,
    private val onAppointmentChanged: () -> Unit
) : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    class AppointmentViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val petNameText: TextView =
            itemView.findViewById(R.id.petNameText)

        val typeText: TextView =
            itemView.findViewById(R.id.typeText)

        val dateText: TextView =
            itemView.findViewById(R.id.dateText)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AppointmentViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_appointment,
                    parent,
                    false
                )

        return AppointmentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return appointmentList.size
    }

    override fun onBindViewHolder(
        holder: AppointmentViewHolder,
        position: Int
    ) {

        val appointment =
            appointmentList[position]

        holder.petNameText.text =
            appointment.petName

        holder.typeText.text =
            "📋 ${appointment.appointmentType}"

        holder.dateText.text =
            "📅 ${appointment.appointmentDate}"

        holder.itemView.setOnLongClickListener {

            AlertDialog.Builder(
                holder.itemView.context
            )
                .setTitle("Randevuyu Sil")
                .setMessage(
                    "${appointment.petName} randevusu silinsin mi?"
                )
                .setPositiveButton("Sil") { _, _ ->

                    val currentPosition =
                        holder.adapterPosition

                    if (
                        currentPosition !=
                        RecyclerView.NO_POSITION
                    ) {

                        appointmentList.removeAt(
                            currentPosition
                        )

                        notifyItemRemoved(
                            currentPosition
                        )

                        onAppointmentChanged()
                    }
                }
                .setNegativeButton(
                    "İptal",
                    null
                )
                .show()

            true
        }
    }
}