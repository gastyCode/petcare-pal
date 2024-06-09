package com.obake.petcarepal.ui.activities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obake.petcarepal.R
import com.obake.petcarepal.data.model.Activity
import com.obake.petcarepal.ui.components.AddItemButton
import com.obake.petcarepal.ui.components.AddItemWithIconDialog
import com.obake.petcarepal.ui.components.Background
import com.obake.petcarepal.ui.components.ItemCard
import com.obake.petcarepal.ui.theme.PetCarePalTheme
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivitiesScreen(activitiesViewModel: ActivitiesViewModel, modifier: Modifier = Modifier) {
    val state = activitiesViewModel.state
    val calendar = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(initialHour = calendar.get(Calendar.HOUR_OF_DAY), initialMinute = calendar.get(Calendar.MINUTE))

    var openDialog by remember { mutableStateOf(false) }
    var openDropdown by remember { mutableStateOf(false) }
    var activityName by remember { mutableStateOf("") }
    var activityType by remember { mutableStateOf("") }
    var activityIcon by remember { mutableIntStateOf(0) }
    
    AddItemWithIconDialog(
        openDialog = openDialog,
        openDropdown = openDropdown,
        nameValue = activityName,
        iconValue = activityType,
        nameLabel =  R.string.activity_name,
        titleLabel = R.string.add_activity,
        timePickerState = timePickerState,
        onAdd = {
            activitiesViewModel.insert(activityName, activityType, activityIcon, timePickerState)
            openDialog = false
                },
        toggleDialog = { openDialog = !openDialog },
        toggleDropdown = { openDropdown = !openDropdown },
        onNameChange = { activityName = it },
        onIconChange = { type, icon ->
            activityType = type
            activityIcon = icon
        }
    )

    Box(
        modifier = Modifier.then(modifier)
    ) {
        Background(modifier = Modifier.matchParentSize())
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .then(modifier),
        ) {
            ActivityList(
                state.activities,
                activitiesViewModel::delete,
                { openDialog = !openDialog },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun ActivityList(list: List<Activity>, deleteActivity: (Activity) -> Unit, toggleDialog: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = stringResource(id = R.string.planned_activities),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 4.dp, 0.dp, 0.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(list) { activity ->
                ItemCard(
                    name = activity.name,
                    time = activity.time,
                    type = activity.type,
                    icon = activity.icon,
                    onRemove = { deleteActivity(activity) }
                )
            }
            item {
                AddItemButton(
                    onClick = toggleDialog
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivitiesScreenPreview() {
    PetCarePalTheme {
    }
}