package com.obake.petcarepal.ui.activities

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obake.petcarepal.R
import com.obake.petcarepal.data.ActivityIcons
import com.obake.petcarepal.ui.components.DropdownMenu
import com.obake.petcarepal.ui.theme.PetCarePalTheme

@Composable
fun ActivitiesScreen(activitiesViewModel: ActivitiesViewModel, modifier: Modifier = Modifier) {
    val state = activitiesViewModel.state

    AddActivityDialog(
        openDialog = state.openDialog,
        onAdd = {
            activitiesViewModel.insert(state.activityName, state.activityType, state.activityIcon)
        },
        toggleDialog = activitiesViewModel::toggleDialog,
        toggleDropdown = activitiesViewModel::toggleDropdown,
        onNameChange = activitiesViewModel::setActivityName,
        onIconChange = activitiesViewModel::setActivityIcon,
        state = activitiesViewModel.state
    )

    Box(
        modifier = Modifier.then(modifier)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .then(modifier),
        ) {
            AddActivityButton(
                onClick = activitiesViewModel::toggleDialog,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.End)
            )
            ActivityList(activitiesViewModel = activitiesViewModel)
        }
    }
}

@Composable
fun AddActivityButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add_activity))
        }
    }
}

@Composable
fun ActivityList(activitiesViewModel: ActivitiesViewModel, modifier: Modifier = Modifier) {
    val list = activitiesViewModel.state.activities.sortedBy { it.time }

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list) { activity ->
            ActivityCard(
                name = activity.name,
                time = activity.time,
                type = activity.type,
                icon = activity.icon,
                onRemove = { activitiesViewModel.delete(activity) },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ActivityCard(name: String, time: String, type: String, icon: Int, onRemove: () -> Unit, modifier: Modifier = Modifier) {
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
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = type,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 8.dp)
                )
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
fun AddActivityDialog(
    openDialog: Boolean,
    onAdd: () -> Unit,
    toggleDialog: () -> Unit,
    toggleDropdown: () -> Unit,
    onNameChange: (String) -> Unit,
    onIconChange: (String, Int) -> Unit,
    state: ActivitiesState
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
            title = { Text(text = stringResource(id = R.string.add_activity)) },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    TextField(
                        value = state.activityName,
                        onValueChange = onNameChange,
                        label = { Text(stringResource(R.string.activity_name)) }
                    )
                    DropdownMenu(
                        value = state.activityType,
                        label = R.string.add_activity,
                        openDropdown = state.openDropdown,
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
                        state = state.timePickerState
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActivitiesScreenPreview() {
    PetCarePalTheme {
    }
}