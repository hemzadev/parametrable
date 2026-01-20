package com.example.parametrable.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Home", style = MaterialTheme.typography.headlineMedium)
        Text("This is the home page content.", style = MaterialTheme.typography.bodyMedium)

        Button(onClick = {}, shape = MaterialTheme.shapes.extraLarge) {
            Text("Primary action")
        }

        Card(shape = MaterialTheme.shapes.large) {
            Column(Modifier.padding(16.dp)) {
                Text("Card title", style = MaterialTheme.typography.titleMedium)
                Text("Card body text...", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
