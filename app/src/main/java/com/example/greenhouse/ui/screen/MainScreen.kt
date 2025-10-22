package com.example.greenhouse.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.greenhouse.ui.theme.DarkGreen
import com.example.greenhouse.ui.theme.White
import com.example.greenhouse.viewmodel.MainViewModel

@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel = viewModel()) {
    val greenhouses by viewModel.greenhouses.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {

            Spacer(modifier = Modifier.height(22.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Skleníky",
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(greenhouses) { greenhouse ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("greenhouse_detail/${greenhouse.id}")
                            }
                            .padding(4.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = com.example.greenhouse.ui.theme.LightGreen
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(greenhouse.name, style = MaterialTheme.typography.titleMedium)
                            Text("Teplota: ${greenhouse.temperature}°C")
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("add_greenhouse") },
            containerColor = DarkGreen,
            contentColor = White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Greenhouse")
        }
    }
}


