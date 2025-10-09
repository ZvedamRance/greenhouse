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
import com.example.greenhouse.viewmodel.AddGreenhouseViewModel

@Composable
fun AddGreenhouseScreen(
    navController: NavHostController,
    viewModel: AddGreenhouseViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var greenhouseId by remember { mutableStateOf("") }
    var greenhouseName by remember { mutableStateOf("") }


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

            if (errorMessage != null) {
                Text(text = "$errorMessage", color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(16.dp))
            }

            TextField(
                value = greenhouseId,
                onValueChange = { greenhouseId = it },
                label = { Text("ID skleníku") },
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
                value = greenhouseName,
                onValueChange = { greenhouseName = it },
                label = { Text("Název skleníku") },
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
                onClick = { viewModel.addGreenhouse(greenhouseName, greenhouseId.ifBlank { null }) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkGreen,
                    contentColor = White
                )
            ) {
                Text(if (isLoading) "Ukládám..." else "Přidat skleník")
            }
        }
    }
}

