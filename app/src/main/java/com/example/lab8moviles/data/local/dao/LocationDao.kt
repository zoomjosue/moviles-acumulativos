package com.example.lab8moviles.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lab8moviles.data.local.entity.LocationEntity

// Josué García - 24918
@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<LocationEntity>)

    @Query("SELECT * FROM locations")
    suspend fun getAllLocations(): List<LocationEntity>

    @Query("SELECT * FROM locations WHERE id = :locationId")
    suspend fun getLocationById(locationId: Int): LocationEntity?
}