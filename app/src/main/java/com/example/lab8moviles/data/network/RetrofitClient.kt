package com.example.lab8moviles.data.network

import com.example.lab8moviles.data.network.dto.CharacterResponseDto
import com.example.lab8moviles.data.network.dto.LocationResponseDto
import retrofit2.http.GET

interface RickAndMortyApiService {

    @GET("character")
    suspend fun getCharacters(): CharacterResponseDto

    @GET("location")
    suspend fun getLocations(): LocationResponseDto
}