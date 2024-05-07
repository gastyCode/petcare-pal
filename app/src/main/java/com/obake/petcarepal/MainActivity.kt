package com.obake.petcarepal

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.obake.petcarepal.data.model.Pet

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var pet: Pet? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pet = intent.getParcelableExtra("pet", Pet::class.java)
        } else {
            pet = intent.getParcelableExtra("pet") as? Pet
        }
        Log.d("SplashMan", pet.toString())

        setContent {
            Application(context = applicationContext, pet = pet)
        }
    }
}