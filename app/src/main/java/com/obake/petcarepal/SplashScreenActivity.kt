package com.obake.petcarepal

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.obake.petcarepal.data.ApplicationDatabase
import com.obake.petcarepal.ui.theme.PetCarePalTheme
import com.obake.petcarepal.ui.splash.SplashScreen
import com.obake.petcarepal.ui.splash.SplashViewModel


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = ApplicationDatabase.getDatabase(applicationContext)
        val splashViewModel = SplashViewModel(database.petDao())

        setContent {
            PetCarePalTheme {
                SplashScreen(splashViewModel)
            }
        }
    }
}