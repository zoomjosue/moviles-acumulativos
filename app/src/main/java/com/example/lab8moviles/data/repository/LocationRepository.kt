package com.example.lab8moviles.data.repository

import com.example.lab8moviles.data.Location
import com.example.lab8moviles.data.LocationDb
import com.example.lab8moviles.data.local.dao.LocationDao
import com.example.lab8moviles.data.local.entity.LocationEntity

class LocationRepository(
    private val locationDao: LocationDao
) {
    private val locationDb = LocationDb()

    suspend fun syncLocations() {
        val locations = locationDb.getAllLocations()
        val locationEntities = locations.map {
            LocationEntity(
                id = it.id,
                name = it.name,
                type = it.type,
                dimension = it.dimension
            )
        }
        locationDao.insertAll(locationEntities)
    }

    suspend fun getAllLocations(): List<Location> {
        val entities = locationDao.getAllLocations()
        return entities.map {
            Location(
                id = it.id,
                name = it.name,
                type = it.type,
                dimension = it.dimension
            )
        }
    }

    suspend fun getLocationById(id: Int): Location? {
        val entity = locationDao.getLocationById(id)
        return entity?.let {
            Location(
                id = it.id,
                name = it.name,
                type = it.type,
                dimension = it.dimension
            )
        }
    }
}