package com.obake.petcarepal.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.obake.petcarepal.data.NavigationScreen

@Composable
fun Navigation(navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        NavigationScreen.screens.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = screen.icon), contentDescription = stringResource(id = screen.resName)) },
                label = { Text(stringResource(id = screen.resName)) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(screen.screen.name)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    value: String,
    @StringRes label: Int,
    openDropdown: Boolean,
    toggleDropdown: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        ExposedDropdownMenuBox(
            expanded = openDropdown,
            onExpandedChange = { toggleDropdown() },
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                value = value,
                onValueChange = { },
                readOnly = true,
                label = { Text(stringResource(label)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = openDropdown) }
            )
            ExposedDropdownMenu(
                expanded = openDropdown,
                onDismissRequest = { toggleDropdown() },
            ) {
                content()
            }
        }
    }
}
