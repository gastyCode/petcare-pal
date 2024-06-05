package com.obake.petcarepal.ui.tips

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obake.petcarepal.R
import com.obake.petcarepal.data.model.Pet
import com.obake.petcarepal.ui.theme.PetCarePalTheme

@Composable
fun TipsScreen(tipsViewModel: TipsViewModel, modifier: Modifier = Modifier) {
    val tip = tipsViewModel.state.currentTip

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        tip?.let {
            TipCard(
                title = it.title,
                description = it.description,
                onNext = tipsViewModel::nextTip,
                onPrevious = tipsViewModel::previousTip,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun TipCard(title: String, description: String, onNext: () -> Unit, onPrevious: () -> Unit, modifier: Modifier = Modifier) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Button(
                        onClick = onPrevious,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = stringResource(id = R.string.previous_tip))
                    }
                    Button(
                        onClick = onNext,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = stringResource(id = R.string.next_tip))
                    }
                }
            }
        }
    }
}