package com.obake.petcarepal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.obake.petcarepal.data.model.NavigationScreens
import com.obake.petcarepal.ui.ActivitiesScreen
import com.obake.petcarepal.ui.CalendarScreen
import com.obake.petcarepal.ui.OverviewScreen
import com.obake.petcarepal.ui.TipsScreen
import com.obake.petcarepal.ui.theme.PetCarePalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetCarePalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            BottomAppBar {
                                Navigation(navController = navController)
                            }
                        }
                    ) { padding ->
                        NavHost(navController = navController, startDestination = NavigationScreens.Home.name) {
                            composable(NavigationScreens.Home.name) {
                                OverviewScreen(modifier = Modifier.padding(padding))
                            }
                            composable(NavigationScreens.Activities.name) {
                                ActivitiesScreen(modifier = Modifier.padding(padding))
                            }
                            composable(NavigationScreens.Calendar.name) {
                                CalendarScreen(modifier = Modifier.padding(padding))
                            }
                            composable(NavigationScreens.Tips.name) {
                                TipsScreen(modifier = Modifier.padding(padding))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        NavigationScreens.entries.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = screen.icon), contentDescription = screen.name) },
                label = { Text(screen.name) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(screen.name)
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    PetCarePalTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomAppBar {

                    }
                }
            ) { padding ->
                NavHost(navController = navController, startDestination = "Landing") {
                    composable("Landing") {
                        ActivitiesScreen(modifier = Modifier.padding(padding))
                    }
                }
            }
        }
    }
}