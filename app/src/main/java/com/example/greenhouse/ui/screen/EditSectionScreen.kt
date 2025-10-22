package com.example.greenhouse.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.greenhouse.ui.theme.DarkGreen
import com.example.greenhouse.ui.theme.LightGreen
import com.example.greenhouse.ui.theme.White
import com.example.greenhouse.viewmodel.EditSectionViewModel
import com.example.greenhouse.viewmodel.SectionDetailViewModel

@Composable
fun EditSectionScreen(
    navController: NavHostController,
    greenhouseId: String,
    sectionId: String,
    sectionDetailViewModel: SectionDetailViewModel = viewModel(),
    viewModel: EditSectionViewModel = viewModel()
) {

    var sectionName by remember { mutableStateOf("") }
    var plantName by remember { mutableStateOf("") }
    var waterWhen by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()
    val success by viewModel.success.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(success) {
        if (success) navController.popBackStack()
    }

    val section by sectionDetailViewModel.section.collectAsState()
    LaunchedEffect(sectionId) {
        sectionDetailViewModel.observeSection(greenhouseId, sectionId)
    }

    LaunchedEffect(section) {
        section?.let {
            sectionName = it.name
            plantName = it.plant
            val waterStr = it.water.toString().takeWhile { c -> c.isDigit() }
            waterWhen = waterStr.take(2)
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
                Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(16.dp))
            }

            TextField(
                value = sectionName,
                onValueChange = { if (it.length <= 25) sectionName = it },
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
                onValueChange = { if (it.length <= 25) plantName = it },
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

            TextField(
                value = waterWhen,
                onValueChange = { if (it.length <= 2) waterWhen = it },
                label = { Text("Zalévat při") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                onClick = { viewModel.editSection(greenhouseId, sectionId, sectionName, plantName, waterWhen) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkGreen,
                    contentColor = White
                )
            ) {
                Text(if (isLoading) "Ukládám..." else "Změnit sekci")
            }
        }
    }
}
