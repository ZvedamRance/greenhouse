package com.example.greenhouse.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.greenhouse.ui.theme.Black
import com.example.greenhouse.ui.theme.DarkGreen
import com.example.greenhouse.ui.theme.LightGreen
import com.example.greenhouse.ui.theme.White
import com.example.greenhouse.viewmodel.GreenhouseDetailViewModel
import com.example.greenhouse.viewmodel.MainViewModel

@Composable
fun GreenhouseDetailScreen(
    navController: NavHostController,
    greenhouseId: String,
    mainViewModel: MainViewModel = viewModel(),
    detailViewModel: GreenhouseDetailViewModel = viewModel()
) {
    val greenhouses by mainViewModel.greenhouses.collectAsState()
    val greenhouse = greenhouses.find { it.id == greenhouseId } ?: return

    val sections by detailViewModel.sections.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(greenhouseId) {
        detailViewModel.observeSections(greenhouseId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = greenhouse.name,
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )


                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Smazat skleník",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "ID: ${greenhouse.id}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Teplota: ${greenhouse.temperature}°C",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(sections) { section ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* pridat detaily sekci */ }
                            .padding(4.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = LightGreen)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(section.name, style = MaterialTheme.typography.titleMedium)
                            Text("Plodina: ${section.plant}")
                            Text("Vlhkost půdy: ${section.moisture} %")
                            Text("Zalévat při: ${section.water} %")
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {navController.navigate("add_section/$greenhouseId")},
            containerColor = DarkGreen,
            contentColor = White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Section")
        }
    }

    /* vyskakovaci okna */

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            containerColor = LightGreen,
            title = { Text("Smazat skleník", color = Black) },
            text = { Text("Opravdu chcete smazat tento skleník? Tato akce je nevratná.", color = Black) },
            confirmButton = {
                TextButton(
                    onClick = {
                        detailViewModel.deleteGreenhouse(greenhouseId) {
                            navController.popBackStack()
                        }
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Black
                    )
                ) {
                    Text("Ano")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Black
                    )
                ) {
                    Text("Ne")
                }
            }
        )
    }
}
