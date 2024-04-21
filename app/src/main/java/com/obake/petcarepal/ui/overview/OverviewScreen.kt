package com.obake.petcarepal.ui.overview

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obake.petcarepal.R
import com.obake.petcarepal.data.model.Pet
import com.obake.petcarepal.ui.theme.PetCarePalTheme

@Composable
fun OverviewScreen(overviewViewModel: OverviewViewModel, modifier: Modifier = Modifier) {
    val pets: State<List<Pet>?> = overviewViewModel.pets.observeAsState()
    val pet = pets.value?.getOrNull(0)

    Box(modifier = Modifier.then(modifier)) {
        pet?.name?.let {
            PetOverview(it, pet.type, pet.date, R.drawable.ic_launcher_background)
        }
    }
}

@Composable
fun PetOverview(name: String, type: String, date: String, @DrawableRes image: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = name,
            modifier = Modifier
                .aspectRatio(1f)
        )
        Card(
            modifier = Modifier
                .fillMaxHeight(0.55f)
                .align(Alignment.BottomCenter)
        ) {
            PetStats(name, type, date)
        }
    }
}

@Composable
fun PetStats(name: String, type: String, date: String) {
    Column(
        modifier = Modifier
            .padding(0.dp, 8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            PetStatIcon(
                text = type,
                icon = Icons.Rounded.Info,
                color = MaterialTheme.colorScheme.primary,
                iconColor = MaterialTheme.colorScheme.onPrimary
            )
            PetStatIcon(
                text = date,
                icon = Icons.Rounded.DateRange,
                color = MaterialTheme.colorScheme.primary,
                iconColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun PetStatIcon(text: String, icon: ImageVector, color: Color, iconColor: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(color = color, shape = MaterialTheme.shapes.extraLarge),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = rememberVectorPainter(image = icon),
                contentDescription = text,
                tint = iconColor
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OverviewScreenPreview() {
    PetCarePalTheme {
    }
}