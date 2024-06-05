package com.obake.petcarepal.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat.startActivity
import com.obake.petcarepal.MainActivity
import com.obake.petcarepal.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(splashViewModel: SplashViewModel) {
    val context = LocalContext.current
    val petState = splashViewModel.pets.observeAsState(initial = null)

    LaunchedEffect(key1 = true) {
        delay(100)

        val pet = petState.value?.firstOrNull()

        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("pet", pet)
        startActivity(context, intent, null)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
    }
}