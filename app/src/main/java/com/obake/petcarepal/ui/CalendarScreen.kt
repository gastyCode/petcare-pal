package com.obake.petcarepal.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.obake.petcarepal.data.model.NavigationItem.Companion.items
import com.obake.petcarepal.ui.theme.PetCarePalTheme
import java.util.Date

@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.then(modifier)) {
        Calendar()
    }
}

@Composable
fun Calendar(modifier: Modifier = Modifier) {
    val list = (1..31).toList()

    LazyVerticalGrid(columns = GridCells.Fixed(7)) {
        items(list) { day ->
            CalendarItem(day = day.toString())
        }
    }
}

@Composable
fun CalendarItem(day: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(text = day)
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    PetCarePalTheme {
        CalendarScreen()
    }
}