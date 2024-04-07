package com.obake.petcarepal.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.obake.petcarepal.R
import com.obake.petcarepal.ui.theme.PetCarePalTheme
import java.sql.Date

@Composable
fun OverviewScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.then(modifier)) {
        PetOverview("Bady", "Dog", Date(103, 4, 6), R.drawable.ic_launcher_background)
    }
}

@Composable
fun PetOverview(name: String, type: String, date: Date, @DrawableRes image: Int) {
    Column {
        Image(
            painter = painterResource(id = image),
            contentDescription = name,
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.medium)
        )
        Text(text = name)
        Text(text = type)
        Text(text = date.toString())
    }
}

@Preview(showBackground = true)
@Composable
fun OverviewScreenPreview() {
    PetCarePalTheme {
        OverviewScreen()
    }
}