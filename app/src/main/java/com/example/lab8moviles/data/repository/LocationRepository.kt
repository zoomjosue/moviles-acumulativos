package com.example.lab8moviles.data.repository

import com.example.lab8moviles.data.Location
import com.example.lab8moviles.data.local.dao.LocationDao
import com.example.lab8moviles.data.local.entity.LocationEntity
import com.example.lab8moviles.data.network.RickAndMortyApiService
import com.example.lab8moviles.data.network.mappers.toEntity

class LocationRepository(
    private val locationDao: LocationDao,
    private val apiService: RickAndMortyApiService
) {

    suspend fun getAllLocations(): List<Location> {
        val localLocations = locationDao.getAllLocations()

        return if (localLocations.isNotEmpty()) {
            localLocations.map { it.toLocation() }
        } else {
            try {
                val response = apiService.getLocations()
                val locationEntities = response.results.map { it.toEntity() }

                locationDao.insertAll(locationEntities)

                locationEntities.map { it.toLocation() }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getLocationById(id: Int): Location? {
        val entity = locationDao.getLocationById(id)
        return entity?.toLocation()
    }

    suspend fun syncLocations() {
        try {
            val response = apiService.getLocations()
            val locationEntities = response.results.map { it.toEntity() }
            locationDao.insertAll(locationEntities)
        } catch (e: Exception) {
            // Manejar error silenciosamente
        }
    }

    private fun LocationEntity.toLocation(): Location {
        return Location(
            id = this.id,
            name = this.name,
            type = this.type,
            dimension = this.dimension
        )
    }
}