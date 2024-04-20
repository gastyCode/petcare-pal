package com.obake.petcarepal.ui.activities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obake.petcarepal.R
import com.obake.petcarepal.ui.theme.PetCarePalTheme

data class Activity(val text: String, val type: String, val date: String, val icon: ImageVector)

@Composable
fun ActivitiesScreen(modifier: Modifier = Modifier) {
    val list = mutableListOf(
        Activity("Activity 1", "Feed", "12:00", Icons.Rounded.Info),
        Activity("Activity 2", "Walk", "13:00", Icons.Rounded.Info),
        Activity("Activity 3", "Feed", "14:00", Icons.Rounded.Info),
        Activity("Activity 4", "Walk", "15:00", Icons.Rounded.Info),
        Activity("Activity 5", "Feed", "16:00", Icons.Rounded.Info),
        Activity("Activity 6", "Walk", "17:00", Icons.Rounded.Info),
        Activity("Activity 7", "Feed", "18:00", Icons.Rounded.Info),
        Activity("Activity 8", "Walk", "19:00", Icons.Rounded.Info),
        Activity("Activity 9", "Feed", "20:00", Icons.Rounded.Info),
        Activity("Activity 10", "Walk", "21:00", Icons.Rounded.Info),
    )

    Box(
        modifier = Modifier.then(modifier)
    ) {
        Column(
            modifier = Modifier.then(modifier),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ActivityList(list = list)
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
                    {  },
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
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Activity")
        }
    }
}

@Composable
fun ActivityList(list: List<Activity>, modifier: Modifier = Modifier) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list) { activity ->
            ActivityCard(
                text = activity.text,
                type = activity.type,
                date = activity.date,
                icon = activity.icon,
                onRemove = {},
                modifier = modifier
            )
        }
    }
}

@Composable
fun ActivityCard(text: String, type: String, date: String, icon: ImageVector, onRemove: () -> Unit, modifier: Modifier = Modifier) {
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
                    contentDescription = type,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 8.dp)
                )
                Column {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Button(onClick = onRemove) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Remove")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivitiesScreenPreview() {
    PetCarePalTheme {
        ActivitiesScreen()
    }
}