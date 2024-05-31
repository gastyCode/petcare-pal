package com.obake.petcarepal.ui.activities

import android.content.Context
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
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obake.petcarepal.R
import com.obake.petcarepal.data.ActivityIcons
import com.obake.petcarepal.data.model.Activity
import com.obake.petcarepal.ui.components.AddItemButton
import com.obake.petcarepal.ui.components.AddItemWithIconDialog
import com.obake.petcarepal.ui.components.DropdownMenu
import com.obake.petcarepal.ui.components.ItemCard
import com.obake.petcarepal.ui.theme.PetCarePalTheme
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivitiesScreen(activitiesViewModel: ActivitiesViewModel, modifier: Modifier = Modifier) {
    val state = activitiesViewModel.state
    
    AddItemWithIconDialog(
        openDialog = state.openDialog,
        openDropdown = state.openDropdown,
        nameValue = state.activityName,
        iconValue = state.activityType,
        label = R.string.add_activity,
        timePickerState = state.timePickerState,
        onAdd = { activitiesViewModel.insert(state.activityName, state.activityType, state.activityIcon) },
        toggleDialog = activitiesViewModel::toggleDialog,
        toggleDropdown = activitiesViewModel::toggleDropdown,
        onNameChange = activitiesViewModel::setActivityName,
        onIconChange = activitiesViewModel::setActivityIcon
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
            AddItemButton(
                onClick = activitiesViewModel::toggleDialog,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.End)
            )
            ActivityList(state.activities, activitiesViewModel::delete)
        }
    }
}

@Composable
fun ActivityList(list: List<Activity>, deleteActivity: (Activity) -> Unit, modifier: Modifier = Modifier) {
    val sortedList = list.sortedBy { it.time }

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sortedList) { activity ->
            ItemCard(
                name = activity.name,
                time = activity.time,
                type = activity.type,
                icon = activity.icon,
                onRemove = { deleteActivity(activity) },
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivitiesScreenPreview() {
    PetCarePalTheme {
    }
}