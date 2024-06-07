package com.obake.petcarepal.ui.addpet

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.obake.petcarepal.R
import com.obake.petcarepal.data.Species
import com.obake.petcarepal.ui.components.Background
import com.obake.petcarepal.ui.components.DropdownMenu
import com.obake.petcarepal.ui.theme.PetCarePalTheme
import com.obake.petcarepal.util.DateHelper
import com.obake.petcarepal.util.StorageHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(addPetViewModel: AddPetViewModel, storageHelper: StorageHelper, modifier: Modifier = Modifier) {
    val state = addPetViewModel.state

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Background(modifier = Modifier.matchParentSize())
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier.padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                AddPetImageInput(state.petImage, storageHelper)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    AddPetTextInput(
                        label = R.string.name,
                        value = state.petName,
                        error = state.inputError,
                        onChange = addPetViewModel::setPetName,
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenu(
                        value = state.petSpecie,
                        label = R.string.species,
                        error = state.inputError,
                        openDropdown = state.openDropdown,
                        toggleDropdown = addPetViewModel::toggleDropdown,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Species.entries.forEach { specie ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = stringResource(id = specie.resName))
                                },
                                onClick = {
                                    addPetViewModel.setPetSpecie(specie.name)
                                    addPetViewModel.toggleDropdown()
                                }
                            )
                        }
                    }
                    AddPetDateInput(
                        label = R.string.date_of_birth,
                        value = state.petBirthdate,
                        error = state.inputError,
                        isPickerOpen = state.openDialog,
                        datePickerState = state.datePickerState,
                        onChange = addPetViewModel::setPetBirthdate,
                        toggleDialog = addPetViewModel::toggleDialog,
                        modifier = Modifier.fillMaxWidth()
                    )
                    AddPetButton(
                        onClick = addPetViewModel::handleAddPet,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

// From Medium: https://readmedium.com/en/https:/medium.com/@cherfaoui_dev/easy-image-picking-no-permissions-required-using-jetpack-compose-733c17163369
@Composable
fun AddPetImageInput(imageName: String, storageHelper: StorageHelper, modifier: Modifier = Modifier) {
    var imageUri: Uri? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) {
            it?.let { uri ->
                context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION )
                scope.launch {
                    storageHelper.saveImage(context, imageName, uri.toString())
                }
            }
        }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        AsyncImage(
            model = imageUri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.medium)
                .clickable { launcher.launch(arrayOf("image/*")) }
        )
        Button(
            onClick = {
                launcher.launch(arrayOf("image/*"))
            },
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(text = "Pick Image")
        }
    }
    LaunchedEffect(key1 = true) {
        storageHelper.loadImage(context, imageName).collect {
            if (it != null)
                imageUri = Uri.parse(it)
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
fun AddPetTextInput(
    @StringRes label: Int,
    value: String,
    error: Boolean = false,
    onChange: (String) -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    TextField(
        label = { Text(text = stringResource(id = label)) },
        value = value,
        onValueChange = onChange,
        singleLine = true,
        isError = error,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetDateInput(
    @StringRes label: Int,
    value: String,
    isPickerOpen: Boolean,
    datePickerState: DatePickerState,
    error: Boolean = false,
    onChange: (String) -> Unit,
    toggleDialog: () -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    TextField(
        label = { Text(text = stringResource(id = label)) },
        value = value,
        onValueChange = onChange,
        readOnly = true,
        isError = error,
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