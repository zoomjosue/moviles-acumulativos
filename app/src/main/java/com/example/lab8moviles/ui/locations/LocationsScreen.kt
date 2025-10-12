package com.example.lab8moviles.ui.locations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab8moviles.data.Location
import com.example.lab8moviles.ui.components.ErrorScreen
import com.example.lab8moviles.ui.components.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    onLocationClick: (Int) -> Unit,
    viewModel: LocationsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Locations") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> {
                LoadingScreen()
            }
            uiState.hasError -> {
                ErrorScreen(
                    errorMessage = "Error al obtener listado de los lugares.\nIntenta de nuevo",
                    onRetry = { viewModel.loadLocations() }
                )
            }
            else -> {
                LazyColumn(modifier = Modifier.padding(padding)) {
                    items(uiState.data) { location ->
                        LocationListItem(location = location, onClick = onLocationClick)
                    }
                }
            }
        }
    }
}

//Josué García Barrera
@Composable
fun LocationListItem(location: Location, onClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(location.id) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LocationTypeIcon(type = location.type, size = 56.dp)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(location.name, fontWeight = FontWeight.Bold)
            Text("${location.type} - ${location.dimension}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun LocationTypeIcon(type: String, size: androidx.compose.ui.unit.Dp = 48.dp) {
    val backgroundColor = when (type.lowercase()) {
        "planet" -> MaterialTheme.colorScheme.primary
        "space station" -> MaterialTheme.colorScheme.secondary
        "microverse" -> MaterialTheme.colorScheme.tertiary
        "tv" -> MaterialTheme.colorScheme.error
        "resort" -> MaterialTheme.colorScheme.outline
        "fantasy town" -> MaterialTheme.colorScheme.inversePrimary
        "dream" -> MaterialTheme.colorScheme.primaryContainer
        "cluster" -> MaterialTheme.colorScheme.secondaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor = when (type.lowercase()) {
        "planet" -> MaterialTheme.colorScheme.onPrimary
        "space station" -> MaterialTheme.colorScheme.onSecondary
        "microverse" -> MaterialTheme.colorScheme.onTertiary
        "tv" -> MaterialTheme.colorScheme.onError
        "resort" -> MaterialTheme.colorScheme.inverseOnSurface
        "fantasy town" -> MaterialTheme.colorScheme.onPrimaryContainer
        "dream" -> MaterialTheme.colorScheme.onPrimaryContainer
        "cluster" -> MaterialTheme.colorScheme.onSecondaryContainer
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = Modifier.size(size).clip(RoundedCornerShape(8.dp)).background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = type.take(2).uppercase(),
            color = textColor,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelMedium
        )
    }
}