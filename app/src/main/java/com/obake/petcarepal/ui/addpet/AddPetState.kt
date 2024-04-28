package com.obake.petcarepal.ui.addpet

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import java.util.Locale

data class AddPetState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val openDialog: Boolean = false,
    val petName: String = "",
    val petSpecie: String = "",
    val petBirthdate: String = "",
    val datePickerState: DatePickerState = DatePickerState(Locale.getDefault()),
)