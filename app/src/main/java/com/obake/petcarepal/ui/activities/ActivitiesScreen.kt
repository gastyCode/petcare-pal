package com.obake.petcarepal.ui.activities

import android.content.res.Resources
import android.content.res.Resources.Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obake.petcarepal.R
import com.obake.petcarepal.data.model.Activity
import com.obake.petcarepal.ui.components.AddItemButton
import com.obake.petcarepal.ui.components.AddItemWithIconDialog
import com.obake.petcarepal.ui.components.ItemCard
import com.obake.petcarepal.ui.theme.PetCarePalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivitiesScreen(activitiesViewModel: ActivitiesViewModel, modifier: Modifier = Modifier) {
    val state = activitiesViewModel.state
    
    AddItemWithIconDialog(
        openDialog = state.openDialog,
        openDropdown = state.openDropdown,
        nameValue = state.activityName,
        iconValue = state.activityType,
        nameLabel =  R.string.activity_name,
        titleLabel = R.string.add_activity,
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
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .then(modifier),
        ) {
            ActivityList(
                state.activities,
                activitiesViewModel::delete,
                activitiesViewModel::toggleDialog,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ActivityList(list: List<Activity>, deleteActivity: (Activity) -> Unit, toggleDialog: () -> Unit, modifier: Modifier = Modifier) {
    val sortedList = list.sortedBy { it.time }

    Card(
        modifier = Modifier.then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
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
                    onRemove = { deleteActivity(activity) }
                )
            }
        }
        AddItemButton(
            onClick = toggleDialog,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 0.dp, 0.dp, 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActivitiesScreenPreview() {
    PetCarePalTheme {
    }
}