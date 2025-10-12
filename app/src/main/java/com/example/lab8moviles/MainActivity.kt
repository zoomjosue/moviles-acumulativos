package com.example.lab8moviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lab8moviles.navigation.*
import com.example.lab8moviles.ui.characterdetail.CharacterDetailScreen
import com.example.lab8moviles.ui.characters.CharactersScreen
import com.example.lab8moviles.ui.locations.LocationsScreen
import com.example.lab8moviles.ui.locationdetail.LocationDetailScreen
import com.example.lab8moviles.ui.profile.ProfileScreen
import com.example.lab8moviles.ui.login.LoginScreen
import com.example.lab8moviles.ui.splash.SplashScreen
import com.example.lab8moviles.ui.theme.Lab8movilesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab8movilesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavHost()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Splash) {
        composable<Splash> {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Login) {
                        popUpTo<Splash> { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Characters) {
                        popUpTo<Splash> { inclusive = true }
                    }
                }
            )
        }

        composable<Login> {
            //Back para cerrar la app
            BackHandler {
                (navController.context as? ComponentActivity)?.finish()
            }

            LoginScreen(
                onStart = {
                    navController.navigate(Characters) {
                        popUpTo<Login> { inclusive = true }
                    }
                },
                logoUrl = "https://static.wikia.nocookie.net/logopedia/images/c/c8/Rick_and_Morty_logo.png/revision/latest?cb=20210617173757&path-prefix=es"
            )
        }

        composable<Characters> {
            MainScreen(navController = navController, startDestination = "characters")
        }
        composable<Locations> {
            MainScreen(navController = navController, startDestination = "locations")
        }
        composable<Profile> {
            MainScreen(navController = navController, startDestination = "profile")
        }

        // Pantallas de detalle
        composable<CharacterDetail> { backStackEntry ->
            val detail = backStackEntry.toRoute<CharacterDetail>()
            CharacterDetailScreen(
                characterId = detail.characterId,
                onBack = { navController.popBackStack() }
            )
        }

        composable<LocationDetail> { backStackEntry ->
            val detail = backStackEntry.toRoute<LocationDetail>()
            LocationDetailScreen(
                locationId = detail.locationId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    startDestination: String
) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    //Back para cerrar la app desde Characters screen
    BackHandler {
        (navController.context as? ComponentActivity)?.finish()
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomNavigationItem.values().forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            bottomNavController.navigate(item.route) {
                                popUpTo(bottomNavController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("characters") {
                CharactersScreen(
                    onCharacterClick = { id ->
                        navController.navigate(CharacterDetail(id))
                    }
                )
            }
            composable("locations") {
                LocationsScreen(
                    onLocationClick = { id ->
                        navController.navigate(LocationDetail(id))
                    }
                )
            }
            composable("profile") {
                ProfileScreen(
                    onLogout = {
                        navController.navigate(Login) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

enum class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    CHARACTERS("Characters", Icons.Default.Person, "characters"),
    LOCATIONS("Locations", Icons.Default.LocationOn, "locations"),
    PROFILE("Profile", Icons.Default.AccountCircle, "profile")
}