package com.example.greenhouse.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.greenhouse.ui.theme.LightGreen
import com.example.greenhouse.ui.theme.Black
import com.example.greenhouse.viewmodel.MainViewModel
import com.example.greenhouse.viewmodel.SectionDetailViewModel

@Composable
fun SectionDetailScreen(
    navController: NavHostController,
    greenhouseId: String,
    sectionId: String,
    mainViewModel: MainViewModel = viewModel(),
    sectionDetailViewModel: SectionDetailViewModel = viewModel()
) {
    val greenhouses by mainViewModel.greenhouses.collectAsState()
    val greenhouse = greenhouses.find { it.id == greenhouseId } ?: return

    val section by sectionDetailViewModel.section.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    val moisture by sectionDetailViewModel.moisture.collectAsState()


    LaunchedEffect(sectionId) {
        sectionDetailViewModel.observeSection(greenhouseId, sectionId)
        sectionDetailViewModel.observeMoisture(greenhouseId, sectionId)
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
                    text = section?.name ?: "Název neuveden",
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )


                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Smazat sekci",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))


            section?.let { sec ->
                Text(
                    text = "ID sekce: ${sec.id}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Teplota: ${greenhouse.temperature}°C",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Plodina: ${sec.plant}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = moisture?.let { "Aktuální vlhkost: $it %" } ?: "Aktuální vlhkost: Žádný záznam ze senzoru",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Zalévat při: ${sec.water}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } ?: run {
                Text("Neuvedeno")
            }
        }

            Spacer(modifier = Modifier.height(16.dp))


        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                containerColor = LightGreen,
                title = { Text("Smazat sekci", color = Black) },
                text = { Text("Opravdu chcete smazat tuto sekci? Tato akce je nevratná.", color = Black) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            navController.popBackStack()
                            sectionDetailViewModel.deleteSection(greenhouseId,sectionId)
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
}
