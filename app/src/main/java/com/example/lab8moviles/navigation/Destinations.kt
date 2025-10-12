package com.example.lab8moviles.navigation

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Characters

@Serializable
data class CharacterDetail(val characterId: Int)

@Serializable
object Locations

@Serializable
data class  LocationDetail(val locationId: Int)

@Serializable
object Profile

@Serializable
object Splash