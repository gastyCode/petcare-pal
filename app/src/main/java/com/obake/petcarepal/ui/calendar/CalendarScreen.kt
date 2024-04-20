package com.obake.petcarepal.ui.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.obake.petcarepal.ui.theme.PetCarePalTheme
import java.util.Locale

@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.then(modifier)) {
        ToggleButton(onIcon = Icons.Default.KeyboardArrowDown, offIcon = Icons.Default.KeyboardArrowUp)
        Calendar(isVisible = true)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(isVisible: Boolean, modifier: Modifier = Modifier) {
    if (isVisible) {
        DatePicker(
            state = DatePickerState(locale = Locale.getDefault()),
            modifier = modifier
        )
    }
}

@Composable
fun ToggleButton(onIcon: ImageVector, offIcon: ImageVector, isOn: Boolean = false) {
    var switch by remember { mutableStateOf(isOn) }
    Button(onClick = { switch = !switch }) {
        if (switch) {
            Icon(imageVector = onIcon, contentDescription = "On")
        } else {
            Icon(imageVector = offIcon, contentDescription = "Off")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalendarScreenPreview() {
    PetCarePalTheme {
        CalendarScreen()
    }
}