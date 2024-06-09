package com.obake.petcarepal.widget

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.lifecycle.asLiveData
import com.obake.petcarepal.MainActivity
import com.obake.petcarepal.R
import com.obake.petcarepal.SplashScreenActivity
import com.obake.petcarepal.data.ApplicationDatabase
import com.obake.petcarepal.data.model.Pet

class Widget: GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val database = ApplicationDatabase.getDatabase(context)
        var pet: Pet? = null
        database.petDao().getAll().asLiveData().observeForever {
            pet = it.firstOrNull()
        }

        provideContent {
            GlanceTheme {
                Content(context, pet!!)
            }
        }
    }

    @Composable
    private fun Content(context: Context, pet: Pet) {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(GlanceTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = context.getString(R.string.pet_overview),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = TextUnit(18f, TextUnitType.Sp))
            )
            Text(text = pet.name)
            Text(text = pet.specie)
            Text(text = pet.birthdate)
            Button(
                text = context.getString(R.string.launch_app),
                style = TextStyle(fontSize = TextUnit(12f, TextUnitType.Sp)),
                onClick = actionStartActivity<SplashScreenActivity>(),
                modifier = GlanceModifier.width(100.dp)
            )
        }
    }
}