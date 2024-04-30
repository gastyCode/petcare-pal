package com.obake.petcarepal.ui.addpet

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.obake.petcarepal.R
import com.obake.petcarepal.data.Screen
import com.obake.petcarepal.ui.theme.PetCarePalTheme
import com.obake.petcarepal.util.DateHelper
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(addPetViewModel: AddPetViewModel, modifier: Modifier = Modifier) {
    val state = addPetViewModel.state

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.add_pet),
                style = MaterialTheme.typography.headlineMedium
            )

            // TODO: Image
            var imageUri: Uri? = null
            val galleryLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
                onResult = { uri ->
                    imageUri = uri
                }
            )

            SubcomposeAsyncImage(
                model = imageUri,
                loading = { CircularProgressIndicator() },
                contentDescription = "Pet Image",
                onError = { error ->
                    Log.e("Image", "Error loading image ${error.result}")
                },
                modifier = Modifier.size(100.dp)
            )
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text(text = "Open Image")
            }

            AddPetTextInput(
                stringResource(id = R.string.name),
                state.petName,
                onChange = addPetViewModel::setPetName
            )
            AddPetTextInput(
                stringResource(id = R.string.species),
                state.petSpecie,
                onChange = addPetViewModel::setPetSpecie
            )
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
        modifier = modifier,
        singleLine = true
    )
}

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
    // TODO: Add click to TextField
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
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    toggleDialog()
                }
            }
    )
    if (isPickerOpen) {
        DatePickerDialog(
            onDismissRequest = toggleDialog,
            confirmButton = {
                Button(onClick = {
                    onChange(DateHelper.millisToDate(datePickerState.selectedDateMillis ?: 0))
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