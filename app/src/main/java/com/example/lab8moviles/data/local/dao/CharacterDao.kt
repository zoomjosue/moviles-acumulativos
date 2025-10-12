package com.example.lab8moviles.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lab8moviles.data.local.entity.CharacterEntity

// Josué García - 24918
@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE id = :characterId")
    suspend fun getCharacterById(characterId: Int): CharacterEntity?
}