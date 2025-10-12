package com.example.lab8moviles.data.repository

import com.example.lab8moviles.data.Character
import com.example.lab8moviles.data.CharacterDb
import com.example.lab8moviles.data.local.dao.CharacterDao
import com.example.lab8moviles.data.local.entity.CharacterEntity

class CharacterRepository(
    private val characterDao: CharacterDao
) {
    private val characterDb = CharacterDb()

    suspend fun syncCharacters() {
        val characters = characterDb.getAllCharacters()
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
        characterDao.insertAll(characterEntities)
    }

    suspend fun getAllCharacters(): List<Character> {
        val entities = characterDao.getAllCharacters()
        return entities.map {
            Character(
                id = it.id,
                name = it.name,
                status = it.status,
                species = it.species,
                gender = it.gender,
                image = it.image
            )
        }
    }

    suspend fun getCharacterById(id: Int): Character? {
        val entity = characterDao.getCharacterById(id)
        return entity?.let {
            Character(
                id = it.id,
                name = it.name,
                status = it.status,
                species = it.species,
                gender = it.gender,
                image = it.image
            )
        }
    }
}