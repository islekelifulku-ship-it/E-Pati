package com.example.e_pati


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class PetAdapter(
    private val petList: ArrayList<PetModel>,
    private val onPetDeleted: () -> Unit
) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    class PetViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val petImage: ImageView =
            itemView.findViewById(R.id.petImage)

        val petName: TextView =
            itemView.findViewById(R.id.petName)

        val petType: TextView =
            itemView.findViewById(R.id.petType)

        val noteButton: MaterialButton =
            itemView.findViewById(R.id.noteButton)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PetViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_pet,
                    parent,
                    false
                )

        return PetViewHolder(view)

    }

    override fun getItemCount(): Int {

        return petList.size

    }

    override fun onBindViewHolder(
        holder: PetViewHolder,
        position: Int
    ) {

        val pet = petList[position]

        holder.petName.text = pet.petName

        holder.petType.text = pet.petType

        holder.petImage.setImageResource(
            pet.petImage
        )

        holder.noteButton.setOnClickListener {

            val context = holder.itemView.context

            val editText =
                android.widget.EditText(context)

            editText.hint =
                "Örn: Tavuklu mama seviyor"

            editText.setText(pet.petNote)

            AlertDialog.Builder(context)
                .setTitle("${pet.petName} Notları")
                .setView(editText)
                .setPositiveButton("Kaydet") { _, _ ->

                    pet.petNote =
                        editText.text.toString()

                    notifyItemChanged(position)

                }
                .setNegativeButton(
                    "İptal",
                    null
                )
                .show()

        }

        holder.itemView.setOnLongClickListener {

            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Evcil Hayvanı Sil")
                .setMessage(
                    "${pet.petName} silinsin mi?"
                )
                .setPositiveButton("Sil") { _, _ ->

                    val currentPosition =
                        holder.adapterPosition

                    if (
                        currentPosition !=
                        RecyclerView.NO_POSITION
                    ) {

                        petList.removeAt(
                            currentPosition
                        )

                        notifyItemRemoved(
                            currentPosition
                        )
                        onPetDeleted()

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