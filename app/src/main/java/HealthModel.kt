package com.example.e_pati

data class HealthModel(

    val petName: String,
    val weight: String,
    val vaccine: String,
    val vetDate: String,
    val neutered: String,
    var isExpanded: Boolean = false

)