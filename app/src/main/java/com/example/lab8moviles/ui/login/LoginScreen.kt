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
import com.example.lab8moviles.data.datastore.UserPreferencesManager
import com.example.lab8moviles.data.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onStart: () -> Unit, @DrawableRes logoRes: Int? = null, logoUrl: String? = null) {
    val context = LocalContext.current
    val userPreferencesManager = remember { UserPreferencesManager(context) }
    val authRepository = remember { AuthRepository(userPreferencesManager) }

    val scope = rememberCoroutineScope()
    var userName by remember { mutableStateOf("") }
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

                // Logo
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

                Spacer(Modifier.height(32.dp))

                // Campo de texto para el nombre
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Nombre") },
                    placeholder = { Text("Ingresa tu nombre") },
                    modifier = Modifier
                        .width(280.dp)
                        .padding(horizontal = 16.dp),
                    enabled = !isLoading,
                    singleLine = true
                )

                Spacer(Modifier.height(24.dp))

                if (isLoading) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(16.dp))
                    Text("Iniciando sesión...", style = MaterialTheme.typography.bodyMedium)
                } else {
                    Button(
                        onClick = {
                            if (userName.isNotBlank()) {
                                scope.launch {
                                    isLoading = true

                                    // Solo guardar el nombre de usuario
                                    authRepository.login(userName.trim())

                                    // Pequeño delay para la UI
                                    delay(1000)

                                    isLoading = false
                                    onStart()
                                }
                            }
                        },
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.width(220.dp).height(48.dp),
                        enabled = !isLoading && userName.isNotBlank()
                    ) {
                        Text(text = "Iniciar sesión")
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