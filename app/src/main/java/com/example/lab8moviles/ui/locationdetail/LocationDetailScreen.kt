package com.example.lab8moviles.ui.locationdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab8moviles.data.LocationDb

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailScreen(locationId: Int, onBack: () -> Unit) {
    val db = LocationDb()
    val location = db.getLocationById(locationId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Location Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(painter = painterResource(android.R.drawable.ic_menu_revert), contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            //Icono
            LocationIcon(type = location.type)

            Spacer(Modifier.height(24.dp))

            //Nombre
            Text(
                text = location.name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            //Info
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            ) {
                InfoRow(label = "ID:", value = location.id.toString())
                InfoRow(label = "Type:", value = location.type)
                InfoRow(label = "Dimension:", value = location.dimension)
            }
        }
    }
}

@Composable
fun LocationIcon(type: String) {
    val backgroundColor = when (type.lowercase()) {
        "planet" -> MaterialTheme.colorScheme.primary
        "space station" -> MaterialTheme.colorScheme.secondary
        "microverse" -> MaterialTheme.colorScheme.tertiary
        "tv" -> MaterialTheme.colorScheme.error
        "resort" -> MaterialTheme.colorScheme.outline
        "fantasy town" -> MaterialTheme.colorScheme.inversePrimary
        "dream" -> MaterialTheme.colorScheme.primaryContainer
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
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = type.take(3).uppercase(),
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = value,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodySmall
        )
    }
}