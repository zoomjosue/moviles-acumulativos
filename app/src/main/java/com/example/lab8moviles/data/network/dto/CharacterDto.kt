package com.example.lab8moviles.data.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "status") val status: String,
    @Json(name = "species") val species: String,
    @Json(name = "type") val type: String?,
    @Json(name = "gender") val gender: String,
    @Json(name = "origin") val origin: LocationReferenceDto?,
    @Json(name = "location") val location: LocationReferenceDto?,
    @Json(name = "image") val image: String,
    @Json(name = "episode") val episode: List<String>?,
    @Json(name = "url") val url: String?,
    @Json(name = "created") val created: String?
)

@JsonClass(generateAdapter = true)
data class LocationReferenceDto(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

@JsonClass(generateAdapter = true)
data class CharacterResponseDto(
    @Json(name = "info") val info: InfoDto,
    @Json(name = "results") val results: List<CharacterDto>
)

@JsonClass(generateAdapter = true)
data class InfoDto(
    @Json(name = "count") val count: Int,
    @Json(name = "pages") val pages: Int,
    @Json(name = "next") val next: String?,
    @Json(name = "prev") val prev: String?
)