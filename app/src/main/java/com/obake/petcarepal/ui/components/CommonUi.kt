package com.obake.petcarepal.ui.components

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.obake.petcarepal.R
import com.obake.petcarepal.data.ActivityIcons
import com.obake.petcarepal.data.NavigationScreen

@Composable
fun Background(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@Composable
fun Navigation(navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        NavigationScreen.screens.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = screen.icon), contentDescription = stringResource(id = screen.resName)) },
                label = { Text(stringResource(id = screen.resName)) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(screen.screen.name)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    value: String,
    @StringRes label: Int,
    openDropdown: Boolean,
    error: Boolean = false,
    toggleDropdown: () -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        ExposedDropdownMenuBox(
            expanded = openDropdown,
            onExpandedChange = { toggleDropdown() }
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = value,
                onValueChange = { },
                readOnly = true,
                isError = error,
                label = { Text(stringResource(label)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = openDropdown) }
            )
            ExposedDropdownMenu(
                expanded = openDropdown,
                onDismissRequest = { toggleDropdown() }
            ) {
                content()
            }
        }
    }
}

@Composable
fun AddItemButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add))
        }
    }
}

@Composable
fun ItemCard(
    name: String,
    time: String,
    type: String? = null,
    icon: Int? = null,
    onRemove: () -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null && type != null) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = type,
                        modifier = Modifier
                            .size(48.dp)
                            .padding(end = 8.dp)
                    )
                }
                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = time,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Button(onClick = onRemove) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = stringResource(id = R.string.remove))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemWithIconDialog(
    openDialog: Boolean,
    openDropdown: Boolean,
    inputError: Boolean,
    @StringRes nameLabel: Int,
    nameValue: String,
    iconValue: String,
    @StringRes titleLabel: Int,
    timePickerState: TimePickerState,
    onAdd: () -> Unit,
    toggleDialog: () -> Unit,
    toggleDropdown: () -> Unit,
    onNameChange: (String) -> Unit,
    onIconChange: (String, Int) -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = toggleDialog,
            dismissButton = {
                Button(onClick = toggleDialog) {
                    Text(text = stringResource(id = R.string.cancel))
                } },
            confirmButton = {
                Button(onClick = onAdd) {
                    Text(text = stringResource(id = R.string.add))
                } },
            title = { Text(text = stringResource(id = titleLabel)) },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    TextField(
                        value = nameValue,
                        isError = inputError,
                        onValueChange = onNameChange,
                        label = { Text(stringResource(id = nameLabel)) }
                    )
                    DropdownMenu(
                        value = iconValue,
                        label = titleLabel,
                        openDropdown = openDropdown,
                        toggleDropdown = toggleDropdown
                    ) {
                        ActivityIcons.entries.forEach { icon ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = icon.icon),
                                            contentDescription = stringResource(id = icon.resName),
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Text(text = stringResource(id = icon.resName))
                                    }
                                },
                                onClick = {
                                    onIconChange(icon.name, icon.icon)
                                    toggleDropdown()
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                    TimeInput(
                        state = timePickerState
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemDialog(
    openDialog: Boolean,
    inputError: Boolean,
    @StringRes nameLabel: Int,
    nameValue: String,
    @StringRes titleLabel: Int,
    timePickerState: TimePickerState,
    onAdd: () -> Unit,
    toggleDialog: () -> Unit,
    onNameChange: (String) -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = toggleDialog,
            dismissButton = {
                Button(onClick = toggleDialog) {
                    Text(text = stringResource(id = R.string.cancel))
                } },
            confirmButton = {
                Button(onClick = onAdd) {
                    Text(text = stringResource(id = R.string.add))
                } },
            title = { Text(text = stringResource(id = titleLabel)) },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    TextField(
                        value = nameValue,
                        isError = inputError,
                        onValueChange = onNameChange,
                        label = { Text(stringResource(id = nameLabel)) }
                    )
                    TimeInput(
                        state = timePickerState
                    )
                }
            }
        )
    }
}