package com.example.lab8moviles.ui.login

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lab8moviles.data.CharacterDb
import com.example.lab8moviles.data.LocationDb
import com.example.lab8moviles.data.local.AppDatabase
import com.example.lab8moviles.data.local.entity.CharacterEntity
import com.example.lab8moviles.data.local.entity.LocationEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Josué García - 24918
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onStart: () -> Unit, @DrawableRes logoRes: Int? = null, logoUrl: String? = null) {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(Modifier.height(12.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(Modifier.height(60.dp))
                if (logoRes != null) {
                    Image(
                        painter = painterResource(id = logoRes),
                        contentDescription = "Logo",
                        modifier = Modifier.size(220.dp)
                    )
                } else if (!logoUrl.isNullOrBlank()) {
                    AsyncImage(model = logoUrl, contentDescription = "Logo", modifier = Modifier.size(220.dp))
                } else {
                    Box(
                        modifier = Modifier.size(220.dp).clip(RoundedCornerShape(16.dp)).background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Logo aquí", textAlign = TextAlign.Center)
                    }
                }
                Spacer(Modifier.height(24.dp))

                if (isLoading) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(16.dp))
                    Text("Sincronizando datos...", style = MaterialTheme.typography.bodyMedium)
                } else {
                    Button(
                        onClick = {
                            scope.launch {
                                isLoading = true

                                // Obtener datos de las clases DB
                                val characterDb = CharacterDb()
                                val locationDb = LocationDb()

                                val characters = characterDb.getAllCharacters()
                                val locations = locationDb.getAllLocations()

                                // Convertir a entidades
                                val characterEntities = characters.map {
                                    CharacterEntity(
                                        id = it.id,
                                        name = it.name,
                                        status = it.status,
                                        species = it.species,
                                        gender = it.gender,
                                        image = it.image
                                    )
                                }

                                val locationEntities = locations.map {
                                    LocationEntity(
                                        id = it.id,
                                        name = it.name,
                                        type = it.type,
                                        dimension = it.dimension
                                    )
                                }

                                // Insertar en Room
                                database.characterDao().insertAll(characterEntities)
                                database.locationDao().insertAll(locationEntities)

                                // Delay de 4 segundos
                                delay(4000)

                                isLoading = false
                                onStart()
                            }
                        },
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.width(220.dp).height(48.dp),
                        enabled = !isLoading
                    ) {
                        Text(text = "Entrar")
                    }
                }
            }
            Text(
                text = "Josué García - 24918",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}