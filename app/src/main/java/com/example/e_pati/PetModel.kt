package com.example.e_pati

data class PetModel(

    val petName: String,
    val petType: String,
    val petImage: Int,
    var petNote: String = ""

)