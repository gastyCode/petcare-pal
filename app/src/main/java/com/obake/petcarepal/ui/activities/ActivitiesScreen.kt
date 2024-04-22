package com.obake.petcarepal.ui.activities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obake.petcarepal.R
import com.obake.petcarepal.data.model.Activity
import com.obake.petcarepal.ui.theme.PetCarePalTheme
import java.util.Date

@Composable
fun ActivitiesScreen(activitiesViewModel: ActivitiesViewModel, modifier: Modifier = Modifier) {
    val state = activitiesViewModel.state

    Box(
        modifier = Modifier.then(modifier)
    ) {
        AddActivityDialog(
            openDialog = state.openDialog,
            onAdd = {
                activitiesViewModel.insert(state.activityName)
            },
            onDismiss = activitiesViewModel::toggleDialog,
            onNameChange = activitiesViewModel::setActivityName,
            state = activitiesViewModel.state
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .then(modifier),
        ) {
            ActivityList(activitiesViewModel = activitiesViewModel)
        }

        BottomAppBar(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                AddActivityButton(
                    onClick = activitiesViewModel::toggleDialog,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
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
            Text(
                text = stringResource(id = R.string.add_activity),
                style = MaterialTheme.typography.titleMedium
            )
            Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add_activity))
        }
    }
}

@Composable
fun ActivityList(activitiesViewModel: ActivitiesViewModel, modifier: Modifier = Modifier) {
    val list = activitiesViewModel.state.activities

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list) { activity ->
            ActivityCard(
                name = activity.name,
                time = activity.time,
                icon = Icons.Default.AccountCircle,
                onRemove = { activitiesViewModel.delete(activity) },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ActivityCard(name: String, time: String, icon: ImageVector, onRemove: () -> Unit, modifier: Modifier = Modifier) {
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
                    imageVector = icon,
                    contentDescription = null,
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
    onDismiss: () -> Unit,
    onNameChange: (String) -> Unit,
    state: ActivitiesState
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text(text = stringResource(id = R.string.cancel))
                } },
            confirmButton = {
                Button(onClick = onAdd) {
                    Text(text = stringResource(id = R.string.add))
                } },
            title = { Text(text = stringResource(id = R.string.add_activity)) },
            text = {
                Column {
                    TextField(
                        value = state.activityName,
                        onValueChange = onNameChange,
                        label = { Text(stringResource(R.string.activity_name)) }
                    )
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