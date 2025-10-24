package com.example.lab8moviles.data.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "type") val type: String,
    @Json(name = "dimension") val dimension: String,
    @Json(name = "residents") val residents: List<String>?,
    @Json(name = "url") val url: String?,
    @Json(name = "created") val created: String?
)

@JsonClass(generateAdapter = true)
data class LocationResponseDto(
    @Json(name = "info") val info: InfoDto,
    @Json(name = "results") val results: List<LocationDto>
)