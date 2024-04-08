package com.obake.petcarepal.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obake.petcarepal.ui.theme.PetCarePalTheme

@Composable
fun TipsScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.then(modifier)) {
        TipCard(title = "Hello", description = "You should focus on activity")
    }
}

@Composable
fun TipCard(title: String, description: String, modifier: Modifier = Modifier) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipsScreenPreview() {
    PetCarePalTheme {
        TipsScreen()
    }
}