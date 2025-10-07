package com.example.greenhouse.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.greenhouse.ui.theme.DarkGreen
import com.example.greenhouse.ui.theme.LightGreen
import com.example.greenhouse.ui.theme.White
import com.example.greenhouse.viewmodel.AddSectionViewModel

@Composable
fun AddSectionScreen(
    navController: NavHostController,
    greenhouseId: String,
    viewModel: AddSectionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var sectionName by remember { mutableStateOf("") }
    var plantName by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()
    val success by viewModel.success.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    if (success) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = sectionName,
                onValueChange = { sectionName = it },
                label = { Text("Název sekce") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = DarkGreen,
                    unfocusedTextColor = DarkGreen,
                    focusedContainerColor = LightGreen,
                    unfocusedContainerColor = LightGreen,
                    cursorColor = DarkGreen,
                    focusedIndicatorColor = DarkGreen,
                    unfocusedIndicatorColor = DarkGreen.copy(alpha = 0.5f),
                    focusedLabelColor = DarkGreen,
                    unfocusedLabelColor = DarkGreen.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = plantName,
                onValueChange = { plantName = it },
                label = { Text("Název rostliny") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = DarkGreen,
                    unfocusedTextColor = DarkGreen,
                    focusedContainerColor = LightGreen,
                    unfocusedContainerColor = LightGreen,
                    cursorColor = DarkGreen,
                    focusedIndicatorColor = DarkGreen,
                    unfocusedIndicatorColor = DarkGreen.copy(alpha = 0.5f),
                    focusedLabelColor = DarkGreen,
                    unfocusedLabelColor = DarkGreen.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.addSection(greenhouseId, sectionName, plantName) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkGreen,
                    contentColor = White
                )
            ) {
                Text(if (isLoading) "Ukládám..." else "Přidat sekci")
            }

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
