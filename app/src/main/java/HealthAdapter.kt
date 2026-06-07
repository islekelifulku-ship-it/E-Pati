package com.example.e_pati

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class HealthAdapter(
    private val healthList: ArrayList<HealthModel>,
    private val onHealthChanged: () -> Unit
) : RecyclerView.Adapter<HealthAdapter.HealthViewHolder>() {

    class HealthViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val petName: TextView =
            itemView.findViewById(R.id.petName)

        val petWeight: TextView =
            itemView.findViewById(R.id.petWeight)

        val petVaccine: TextView =
            itemView.findViewById(R.id.petVaccine)

        val petVet: TextView =
            itemView.findViewById(R.id.petVet)

        val petNeutered: TextView =
            itemView.findViewById(R.id.petNeutered)

        val detailLayout: LinearLayout =
            itemView.findViewById(R.id.detailLayout)

        val updateButton: MaterialButton =
            itemView.findViewById(R.id.updateButton)

        val deleteButton: MaterialButton =
            itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HealthViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_health,
                    parent,
                    false
                )

        return HealthViewHolder(view)
    }

    override fun getItemCount(): Int {
        return healthList.size
    }

    override fun onBindViewHolder(
        holder: HealthViewHolder,
        position: Int
    ) {

        val health = healthList[position]

        holder.petName.text = health.petName

        holder.petWeight.text =
            "⚖️ Kilo: ${health.weight}"

        holder.petVaccine.text =
            "💉 Aşı: ${health.vaccine}"

        holder.petVet.text =
            "🏥 Veteriner: ${health.vetDate}"

        holder.petNeutered.text =
            "✂️ Kısırlaştırma: ${health.neutered}"

        holder.detailLayout.visibility =
            if (health.isExpanded)
                View.VISIBLE
            else
                View.GONE

        holder.itemView.setOnClickListener {

            health.isExpanded =
                !health.isExpanded

            notifyItemChanged(position)
        }

        holder.updateButton.setOnClickListener {

            val context =
                holder.itemView.context

            val dialogView =
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.dialog_update_health,
                        null
                    )

            val weightEdit =
                dialogView.findViewById<android.widget.EditText>(
                    R.id.weightEditText
                )

            val vaccineEdit =
                dialogView.findViewById<android.widget.EditText>(
                    R.id.vaccineEditText
                )

            val vetEdit =
                dialogView.findViewById<android.widget.EditText>(
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
                    context,
                    android.R.layout.simple_spinner_dropdown_item,
                    neuteredOptions
                )

            neuteredSpinner.setSelection(
                if (health.neutered == "Evet")
                    0
                else
                    1
            )

            weightEdit.setText(health.weight)
            vaccineEdit.setText(health.vaccine)
            vetEdit.setText(health.vetDate)

            AlertDialog.Builder(context)
                .setTitle("${health.petName} Güncelle")
                .setView(dialogView)
                .setPositiveButton("Kaydet") { _, _ ->

                    healthList[position] =
                        HealthModel(
                            health.petName,
                            weightEdit.text.toString(),
                            vaccineEdit.text.toString(),
                            vetEdit.text.toString(),
                            neuteredSpinner.selectedItem.toString(),
                            health.isExpanded
                        )

                    notifyItemChanged(position)

                    onHealthChanged()
                }
                .setNegativeButton(
                    "İptal",
                    null
                )
                .show()
        }

        holder.deleteButton.setOnClickListener {

            val currentPosition =
                holder.adapterPosition

            if (
                currentPosition !=
                RecyclerView.NO_POSITION
            ) {

                healthList.removeAt(
                    currentPosition
                )

                notifyItemRemoved(
                    currentPosition
                )

                notifyItemRangeChanged(
                    currentPosition,
                    healthList.size
                )

                onHealthChanged()
            }
        }
    }
}