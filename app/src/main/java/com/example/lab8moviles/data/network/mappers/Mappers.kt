package com.example.lab8moviles.data.network.mappers

import com.example.lab8moviles.data.local.entity.CharacterEntity
import com.example.lab8moviles.data.local.entity.LocationEntity
import com.example.lab8moviles.data.network.dto.CharacterDto
import com.example.lab8moviles.data.network.dto.LocationDto

// Mapper de CharacterDto a CharacterEntity
fun CharacterDto.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        gender = this.gender,
        image = this.image
    )
}

// Mapper de LocationDto a LocationEntity
fun LocationDto.toEntity(): LocationEntity {
    return LocationEntity(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension
    )
}