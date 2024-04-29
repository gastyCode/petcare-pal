package com.obake.petcarepal.ui.addpet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obake.petcarepal.R
import com.obake.petcarepal.data.Screen
import com.obake.petcarepal.ui.theme.PetCarePalTheme
import com.obake.petcarepal.util.DateHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// TODO: Center the content
fun AddPetScreen(addPetViewModel: AddPetViewModel, modifier: Modifier = Modifier) {
    val state = addPetViewModel.state

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Text(text = stringResource(id = R.string.add_pet), style = MaterialTheme.typography.headlineMedium)
        // TODO: Image
        AddPetTextInput(stringResource(id = R.string.name), state.petName, onChange = addPetViewModel::setPetName)
        AddPetTextInput(stringResource(id = R.string.species), state.petSpecie, onChange = addPetViewModel::setPetSpecie)
        AddPetDateInput(
            stringResource(id = R.string.date_of_birth),
            state.petBirthdate,
            state.openDialog,
            state.datePickerState,
            addPetViewModel::setPetBirthdate,
            addPetViewModel::toggleDialog
        )
        AddPetButton(onClick = {
            addPetViewModel.insert(state.petName, state.petSpecie, state.petBirthdate)
            addPetViewModel.navigateToNext(Screen.Home.name)
        })
    }
}

@Composable
fun AddPetButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.add_pet))
    }
}

@Composable
fun AddPetTextInput(label: String, value: String, onChange: (String) -> Unit, modifier: Modifier = Modifier) {
    TextField(
        label = { Text(text = label) },
        value = value,
        onValueChange = onChange,
        modifier = modifier
    )
}

// TODO: Fix value of TextField not showing the selected date
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetDateInput(
    label: String,
    value: String,
    isPickerOpen: Boolean,
    datePickerState: DatePickerState,
    onChange: (String) -> Unit,
    toggleDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        label = { Text(text = label) },
        value = value,
        onValueChange = onChange,
        readOnly = true,
        trailingIcon = {
           IconButton(onClick = toggleDialog) {
               Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date Picker")
           }
        },
        modifier = modifier
    )
    if (isPickerOpen) {
        DatePickerDialog(
            onDismissRequest = toggleDialog,
            confirmButton = {
                Button(onClick = {
                    DateHelper.millisToDate(datePickerState.selectedDateMillis ?: 0)
                    toggleDialog()
                }) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview
@Composable
fun AddPetScreenPreview() {
    PetCarePalTheme {
    }
}