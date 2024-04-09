package com.obake.petcarepal.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.obake.petcarepal.data.model.NavigationItem.Companion.items
import com.obake.petcarepal.ui.theme.PetCarePalTheme
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.then(modifier)) {
        DatePicker(state = DatePickerState(locale = Locale.getDefault()))
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    PetCarePalTheme {
        CalendarScreen()
    }
}