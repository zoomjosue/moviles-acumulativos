package com.example.lab8moviles.data.repository

import com.example.lab8moviles.data.Character
import com.example.lab8moviles.data.local.dao.CharacterDao
import com.example.lab8moviles.data.local.entity.CharacterEntity
import com.example.lab8moviles.data.network.RickAndMortyApiService
import com.example.lab8moviles.data.network.mappers.toEntity

class CharacterRepository(
    private val characterDao: CharacterDao,
    private val apiService: RickAndMortyApiService
) {

    suspend fun getAllCharacters(): List<Character> {
        val localCharacters = characterDao.getAllCharacters()

        return if (localCharacters.isNotEmpty()) {
            localCharacters.map { it.toCharacter() }
        } else {
            try {
                val response = apiService.getCharacters()
                val characterEntities = response.results.map { it.toEntity() }

                characterDao.insertAll(characterEntities)

                characterEntities.map { it.toCharacter() }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getCharacterById(id: Int): Character? {
        val entity = characterDao.getCharacterById(id)
        return entity?.toCharacter()
    }

    suspend fun syncCharacters() {
        try {
            val response = apiService.getCharacters()
            val characterEntities = response.results.map { it.toEntity() }
            characterDao.insertAll(characterEntities)
        } catch (e: Exception) {
        }
    }

    private fun CharacterEntity.toCharacter(): Character {
        return Character(
            id = this.id,
            name = this.name,
            status = this.status,
            species = this.species,
            gender = this.gender,
            image = this.image
        )
    }
}